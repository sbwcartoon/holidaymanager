package toy.test.holidaymanager.holiday.application.port.in.command;

import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

public record RemoveCommand(
        HolidayYear year,
        CountryCode countryCode
) {
    public static RemoveCommand from(final int year, final String countryCode) {
        return new RemoveCommand(
                new HolidayYear(year),
                new CountryCode(countryCode)
        );
    }
}
