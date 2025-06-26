package toy.test.holidaymanager.holiday;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerHolidayClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerServerException;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FetchCountryHolidaysParsingFailureTest {

    @Test
    public void 공휴일_fetch_실패_데이터구조변경됨() throws IOException {
        try (MockWebServer server = new MockWebServer()) {
            server.enqueue(new MockResponse()
                    .setResponseCode(200)
                    .addHeader("Content-Type", "application/json")
                    .setBody("파싱_불가_구조"));

            final WebClient webClient = WebClient.builder()
                    .baseUrl(server.url("/").toString())
                    .build();
            final NagerHolidayClient client = new NagerHolidayClient(webClient);

            assertThatThrownBy(() -> client.fetchAll(List.of(2025), List.of("KR")))
                    .isInstanceOf(NagerServerException.class);
        }
    }
}
