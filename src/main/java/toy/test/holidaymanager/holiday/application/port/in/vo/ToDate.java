package toy.test.holidaymanager.holiday.application.port.in.vo;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

public record ToDate(LocalDate value) {
    public static ToDate from(
            final int year,
            final Integer month
    ) {
        return new ToDate(
                YearMonth.of(year, Objects.requireNonNullElse(month, 12)).atEndOfMonth());
    }

    public boolean isValid(final FromDate from) {
        return !value.isBefore(from.value());
    }

    @Override
    @Nonnull
    public String toString() {
        return value.toString();
    }
}
