package toy.test.holidaymanager.holiday.application.port.in.vo;

import jakarta.annotation.Nonnull;

public record HolidayYear(int value) {
    @Override
    @Nonnull
    public String toString() {
        return String.valueOf(value);
    }
}
