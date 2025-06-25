package toy.test.holidaymanager.holiday.application.port.in.command;

import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

public record RenewCommand(
        HolidayYear year,
        CountryCode countryCode
) {
    public static RenewCommand from(final int year, final String countryCode) {
        return new RenewCommand(
                new HolidayYear(year),
                new CountryCode(countryCode)
        );
    }
}
