package toy.test.holidaymanager.holiday.adapter.out.persistence.exception;

public class HolidayNotFoundException extends RuntimeException {
    public HolidayNotFoundException(final int year, final String countryCode) {
        super("Holiday not found for year: " + year + " and country: " + countryCode);
    }
}
