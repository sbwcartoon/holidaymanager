package toy.test.holidaymanager.holiday.domain.vo;

import jakarta.annotation.Nonnull;

public record CountryCode(String value) implements Comparable<CountryCode> {
    @Override
    @Nonnull
    public String toString() {
        return value;
    }

    @Override
    public int compareTo(CountryCode other) {
        return this.value.compareTo(other.value);
    }
}
