package toy.test.holidaymanager.holiday.adapter.out.persistence.exception;

public class HolidayNotFoundException extends RuntimeException {
    public HolidayNotFoundException(int year, String countryCode) {
        super("Holiday not found for year: " + year + " and country: " + countryCode);
    }
}
