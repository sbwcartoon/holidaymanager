package toy.test.holidaymanager.holiday.application.port.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

public interface DateSourceRepository {
    List<Holiday> findByYearAndCountryCode(final int year, final String countryCode) throws JsonProcessingException;
}
