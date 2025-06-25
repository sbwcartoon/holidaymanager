package toy.test.holidaymanager.holiday.adapter.out.nager.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import toy.test.holidaymanager.holiday.adapter.out.nager.dto.NagerHolidayResponse;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerHolidayFetchException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RequiredArgsConstructor
@Component
public class NagerHolidayClient {
    private final ObjectMapper objectMapper;

    public List<NagerHolidayResponse> fetch(final int year, final String countryCode) throws JsonProcessingException {
        final HttpResponse<String> response = getResponse(year, countryCode);

        if (response.statusCode() != HttpStatus.OK.value()) {
            throw new NagerHolidayFetchException();
        }

        return objectMapper.readValue(response.body(), new TypeReference<>() {
        });
    }

    public HttpResponse<String> getResponse(final int year, final String countryCode) {
        try (final HttpClient client = HttpClient.newHttpClient()) {
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://date.nager.at/api/v3/PublicHolidays/" + year + "/" + countryCode))
                    .GET()
                    .build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            throw new NagerHolidayFetchException();
        }
    }
}
