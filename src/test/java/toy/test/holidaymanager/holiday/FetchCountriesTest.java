package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountriesUseCase;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class FetchCountriesTest {
    @Autowired
    private FetchCountriesUseCase fetchCountriesUseCase;

    @Test
    public void 국가코드_fetch_성공() {
        final List<CountryCode> result = fetchCountriesUseCase.fetch();
        assertThat(result).isNotEmpty();
    }
}
