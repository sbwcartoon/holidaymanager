package toy.test.holidaymanager.holiday.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

public interface FetchCountriesUseCase {
    List<CountryCode> fetch() throws JsonProcessingException;
}
