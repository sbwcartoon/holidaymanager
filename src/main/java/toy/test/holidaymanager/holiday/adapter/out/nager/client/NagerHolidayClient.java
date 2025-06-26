package toy.test.holidaymanager.holiday.adapter.out.nager.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.dto.NagerHolidayResponse;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerClientException;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerServerException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
public class NagerHolidayClient {
    private final WebClient webClient;

    public NagerHolidayClient(@Qualifier("nagerWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public CompletableFuture<List<NagerHolidayResponse>> fetchAsync(
            final int year,
            final String countryCode
    ) {
        return webClient.get()
                .uri("/publicholidays/{year}/{countryCode}", year, countryCode)
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
                .bodyToMono(new ParameterizedTypeReference<List<NagerHolidayResponse>>() {
                })
                .onErrorMap(e ->
                                !(e instanceof NagerClientException) && !(e instanceof NagerServerException),
                        ex -> new NagerServerException("Unknown Error", ex)
                )
                .toFuture();
    }

    public List<NagerHolidayResponse> fetchAll(
            final List<Integer> years,
            final List<String> countryCodes
    ) {
        final List<NagerHolidayResponse> result = fetchAllOriginal(years, countryCodes);
        return getSorted(result);
    }

    public List<NagerHolidayResponse> fetchAllOriginal(
            final List<Integer> years,
            final List<String> countryCodes
    ) {
        final List<CompletableFuture<List<NagerHolidayResponse>>> futures = new ArrayList<>();
        for (int year : years) {
            for (String countryCode : countryCodes) {
                futures.add(fetchAsync(year, countryCode));
            }
        }

        try {
            final CompletableFuture<Void> allDone = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            allDone.join();

            return futures.stream()
                    .map(CompletableFuture::join)
                    .flatMap(List::stream)
                    .toList();
        } catch (CancellationException | CompletionException e) {
            if (e.getCause() instanceof NagerClientException cause) {
                throw cause;
            }
            throw new NagerServerException("Error while fetching holidays", e.getCause());
        }
    }

    private List<NagerHolidayResponse> getSorted(final List<NagerHolidayResponse> original) {
        return original.stream()
                .sorted(Comparator.comparing(NagerHolidayResponse::getDate)
                        .thenComparing(NagerHolidayResponse::getCountryCode)
                        .thenComparing(NagerHolidayResponse::getLocalName))
                .toList();
    }
}
