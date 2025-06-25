package toy.test.holidaymanager.holiday.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface RenewGlobalHolidaysUseCase {
    void execute(final int year) throws JsonProcessingException;
}
