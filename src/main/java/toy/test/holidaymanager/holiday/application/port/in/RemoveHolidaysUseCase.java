package toy.test.holidaymanager.holiday.application.port.in;

public interface RemoveHolidaysUseCase {
    void execute(final int year, final String countryCode);
}
