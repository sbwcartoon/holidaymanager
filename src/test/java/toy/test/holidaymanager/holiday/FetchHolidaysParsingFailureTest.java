package toy.test.holidaymanager.holiday;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import toy.test.holidaymanager.holiday.adapter.out.nager.NagerDateAdapter;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerHolidayClient;
import toy.test.holidaymanager.holiday.application.port.in.FetchHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.DateSourceRepository;
import toy.test.holidaymanager.holiday.application.service.FetchHolidaysService;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class FetchHolidaysParsingFailureTest {
    private final NagerHolidayClient client = new NagerHolidayClient(new ObjectMapper());
    private final NagerHolidayClient spyClient = spy(client);
    private final DateSourceRepository repository = new NagerDateAdapter(spyClient);
    private final FetchHolidaysUseCase fetchHolidaysUseCase = new FetchHolidaysService(repository);

    @Test
    public void 공휴일_fetch_실패_데이터구조변경됨() {
        HttpResponse<String> spyResponse = mockHttpResponse();
        when(spyResponse.statusCode()).thenReturn(200);
        when(spyResponse.body()).thenReturn("구조변경된_데이터");
        when(spyClient.getResponse(anyInt(), anyString())).thenReturn(spyResponse);

        assertThatThrownBy(() -> fetchHolidaysUseCase.fetch(2025, "KR"))
                .isInstanceOf(JsonParseException.class);
    }

    @SuppressWarnings("unchecked")
    private HttpResponse<String> mockHttpResponse() {
        return (HttpResponse<String>) mock(HttpResponse.class);
    }
}
