package toy.test.holidaymanager.holiday.application.port.in.vo;

import jakarta.annotation.Nonnull;

import java.time.LocalDate;
import java.util.Objects;

public record FromDate(LocalDate value) {
    public static FromDate from(
            final int year,
            final Integer month
    ) {
        return new FromDate(
                LocalDate.of(year, Objects.requireNonNullElse(month, 1), 1));
    }

    public boolean isValid(final ToDate to) {
        return !value.isAfter(to.value());
    }

    @Override
    @Nonnull
    public String toString() {
        return value.toString();
    }
}
