package toy.test.holidaymanager.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import toy.test.holidaymanager.holiday.adapter.out.nager.NagerCountryAdapter;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerCountryClient;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountriesUseCase;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.application.service.FetchCountriesService;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FetchCountriesTest {
    private final NagerCountryClient client = new NagerCountryClient(new ObjectMapper());
    private final CountrySourceRepository repository = new NagerCountryAdapter(client);
    private final FetchCountriesUseCase fetchCountriesUseCase = new FetchCountriesService(repository);

    @Test
    public void 국가코드_fetch_성공() throws JsonProcessingException {
        List<CountryCode> result = fetchCountriesUseCase.fetch();
        assertThat(result).isNotEmpty();
    }
}
