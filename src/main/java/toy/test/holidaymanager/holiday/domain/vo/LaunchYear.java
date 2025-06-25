package toy.test.holidaymanager.holiday.domain.vo;

import jakarta.annotation.Nonnull;

public record LaunchYear(Integer value) {
    @Override
    @Nonnull
    public String toString() {
        return value.toString();
    }
}
