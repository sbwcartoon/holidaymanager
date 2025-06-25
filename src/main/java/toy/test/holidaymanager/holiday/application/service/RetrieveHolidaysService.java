package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.RetrieveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class RetrieveHolidaysService implements RetrieveHolidaysUseCase {
    private final HolidayRepository holidayRepository;

    @Override
    public Page<Holiday> execute(
            final int year,
            final String countryCode,
            final Integer fromMonth,
            final Integer toMonth,
            final List<String> types,
            final Pageable pageable
    ) {
        return holidayRepository.findAllByCondition(
                new CountryCode(countryCode),
                getStartDate(year, fromMonth),
                getEndDate(year, toMonth),
                Objects.isNull(types) ? null : types.stream().map(HolidayTypeCode::valueOf).toList(),
                pageable
        );
    }

    private LocalDate getStartDate(final int year, final Integer month) {
        return LocalDate.of(year, Objects.requireNonNullElse(month, 1), 1);
    }

    private LocalDate getEndDate(final int year, final Integer month) {
        return YearMonth.of(year, Objects.requireNonNullElse(month, 12)).atEndOfMonth();
    }
}
