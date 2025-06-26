package toy.test.holidaymanager.holiday.adapter.out.nager.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.dto.NagerCountryResponse;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerClientException;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerServerException;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class NagerCountryClient {
    private final WebClient webClient;

    public NagerCountryClient(@Qualifier("nagerWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public CompletableFuture<List<NagerCountryResponse>> fetchAsync() {
        return webClient.get()
                .uri("/AvailableCountries")
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> response.bodyToMono(String.class)
                                .map(NagerClientException::new)
                )
                .onStatus(
                        status -> status.is5xxServerError() || status.value() >= 300,
                        response -> response.bodyToMono(String.class)
                                .map(NagerServerException::new)
                )
                .bodyToMono(new ParameterizedTypeReference<List<NagerCountryResponse>>() {
                })
                .onErrorMap(e ->
                                !(e instanceof NagerClientException) && !(e instanceof NagerServerException),
                        ex -> new NagerServerException("Unknown Error", ex)
                )
                .toFuture();
    }

    public List<NagerCountryResponse> fetchAll() {
        final List<NagerCountryResponse> result = fetchAllOriginal();
        return getSorted(result);
    }

    public List<NagerCountryResponse> fetchAllOriginal() {
        try {
            return fetchAsync().get();
        } catch (InterruptedException | ExecutionException e) {
            if (e.getCause() instanceof NagerClientException cause) {
                throw cause;
            }
            throw new NagerServerException("Error while fetching country codes", e.getCause());
        }
    }

    private List<NagerCountryResponse> getSorted(final List<NagerCountryResponse> original) {
        return original.stream()
                .sorted(Comparator.comparing(NagerCountryResponse::getCountryCode))
                .toList();
    }
}
