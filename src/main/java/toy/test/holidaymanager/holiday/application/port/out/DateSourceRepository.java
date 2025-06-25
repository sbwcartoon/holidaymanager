package toy.test.holidaymanager.holiday.application.port.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;
import toy.test.holidaymanager.holiday.domain.vo.HolidayYear;

import java.util.List;

public interface DateSourceRepository {

    List<Holiday> findByYearAndCountryCode(
            final HolidayYear year,
            final CountryCode countryCode
    ) throws JsonProcessingException;
}
