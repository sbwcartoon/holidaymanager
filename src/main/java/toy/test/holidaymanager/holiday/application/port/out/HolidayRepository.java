package toy.test.holidaymanager.holiday.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository {
    void saveAll(final List<Holiday> holidays);

    Page<Holiday> findAllByCondition(final RetrieveFilterCommand command, final Pageable pageable);
}
