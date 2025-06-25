package toy.test.holidaymanager.holiday.application.port.out;

import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

public interface HolidayRepository {
    void saveAll(final List<Holiday> holidays);
}
