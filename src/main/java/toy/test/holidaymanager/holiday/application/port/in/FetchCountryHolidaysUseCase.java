package toy.test.holidaymanager.holiday.application.port.in;

import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

public interface FetchCountryHolidaysUseCase {
    List<Holiday> fetch(final int year, final String countryCode);
}
