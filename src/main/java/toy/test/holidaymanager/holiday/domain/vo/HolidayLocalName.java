package toy.test.holidaymanager.holiday.domain.vo;

import jakarta.annotation.Nonnull;

public record HolidayLocalName(String value) implements Comparable<HolidayLocalName> {
    @Override
    @Nonnull
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(HolidayLocalName other) {
        return this.value.compareTo(other.value);
    }
}
