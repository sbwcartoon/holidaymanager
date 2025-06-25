package toy.test.holidaymanager.holiday.domain.vo;

import jakarta.annotation.Nonnull;

import java.util.UUID;

public record HolidayId(UUID value) {
    @Override
    @Nonnull
    public String toString() {
        return value.toString();
    }

    public static HolidayId generate() {
        return new HolidayId(UUID.randomUUID());
    }
}
