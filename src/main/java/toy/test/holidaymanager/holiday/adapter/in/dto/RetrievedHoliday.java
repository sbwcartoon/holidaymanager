package toy.test.holidaymanager.holiday.adapter.in.dto;

import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.HolidayCounty;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public record RetrievedHoliday(
        String countryCode,
        String date,
        String localName,
        String name,
        boolean global,
        Integer launchYear,
        List<String> counties,
        List<String> types
) {
    public static RetrievedHoliday from(final Holiday holiday) {
        return new RetrievedHoliday(
                holiday.getCountryCode().value(),
                holiday.getDate().format(DateTimeFormatter.ISO_DATE),
                holiday.getLocalName().value(),
                holiday.getName().value(),
                holiday.getGlobal().value(),
                Objects.isNull(holiday.getLaunchYear()) ? null : holiday.getLaunchYear().value(),
                Objects.isNull(holiday.getCounties()) ? null : holiday.getCounties().stream()
                        .map(HolidayCounty::value).toList(),
                holiday.getTypes().stream().map(Enum::name).toList()
        );
    }
}
