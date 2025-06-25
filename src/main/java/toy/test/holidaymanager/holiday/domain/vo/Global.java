package toy.test.holidaymanager.holiday.domain.vo;

import jakarta.annotation.Nonnull;

public record Global(boolean value) {
    @Override
    @Nonnull
    public String toString() {
        return Boolean.toString(value);
    }
}
