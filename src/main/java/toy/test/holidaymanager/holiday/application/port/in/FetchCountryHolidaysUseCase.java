package toy.test.holidaymanager.holiday.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

public interface FetchCountryHolidaysUseCase {
    List<Holiday> fetch(final int year, final String countryCode) throws JsonProcessingException;
}
