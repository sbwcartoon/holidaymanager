package toy.test.holidaymanager.holiday.application.port.in.command;

import toy.test.holidaymanager.holiday.application.port.in.vo.FromDate;
import toy.test.holidaymanager.holiday.application.port.in.vo.ToDate;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record RetrieveFilterCommand(
        CountryCode countryCode,
        LocalDate startDate,
        LocalDate endDate,
        List<HolidayTypeCode> types
) {
    public static RetrieveFilterCommand from(
            final int year,
            final String countryCode,
            final Integer fromMonth,
            final Integer toMonth,
            final List<String> types
    ) {
        final FromDate from = FromDate.from(year, fromMonth);
        final ToDate to = ToDate.from(year, toMonth);

        if (!from.isValid(to)) {
            throw new IllegalArgumentException("from must be less than to");
        }

        return new RetrieveFilterCommand(
                new CountryCode(countryCode),
                from.value(),
                to.value(),
                Objects.isNull(types) ? null : types.stream().map(HolidayTypeCode::valueOf).toList()
        );
    }
}
