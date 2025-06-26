package toy.test.holidaymanager.holiday.application.port.out;

import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

public interface HolidaySourceRepository {

    List<Holiday> findByYearAndCountryCode(
            final HolidayYear year,
            final CountryCode countryCode
    );

    List<Holiday> findByYearAndCountryCodes(
            final HolidayYear year,
            final List<CountryCode> countryCode
    );
}
