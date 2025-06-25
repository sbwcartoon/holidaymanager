package toy.test.holidaymanager.holiday.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

public interface HolidayRepository {
    void saveAll(final List<Holiday> holidays);

    Page<Holiday> findAllByCondition(final RetrieveFilterCommand command, final Pageable pageable);

    void deleteByYearAndCountryCode(final HolidayYear year, final CountryCode countryCode);
}
