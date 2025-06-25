package toy.test.holidaymanager.holiday.domain.vo;

import jakarta.annotation.Nonnull;

public record HolidayName(String value) {
    @Override
    @Nonnull
    public String toString() {
        return value;
    }
}
