package toy.test.holidaymanager.holiday;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import toy.test.holidaymanager.holiday.adapter.out.nager.NagerCountryAdapter;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerCountryClient;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountriesUseCase;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.application.service.FetchCountriesService;

import java.net.http.HttpResponse;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FetchCountriesParsingFailureTest {
    private final NagerCountryClient client = new NagerCountryClient(new ObjectMapper());
    private final NagerCountryClient spyClient = spy(client);
    private final CountrySourceRepository repository = new NagerCountryAdapter(spyClient);
    private final FetchCountriesUseCase fetchCountriesUseCase = new FetchCountriesService(repository);

    @Test
    public void 국가코드_fetch_실패_데이터구조변경됨() {
        HttpResponse<String> spyResponse = mockHttpResponse();
        when(spyResponse.statusCode()).thenReturn(200);
        when(spyResponse.body()).thenReturn("구조변경된_데이터");
        when(spyClient.getResponse()).thenReturn(spyResponse);

        assertThatThrownBy(fetchCountriesUseCase::fetch)
                .isInstanceOf(JsonParseException.class);
    }

    @SuppressWarnings("unchecked")
    private HttpResponse<String> mockHttpResponse() {
        return (HttpResponse<String>) mock(HttpResponse.class);
    }
}
