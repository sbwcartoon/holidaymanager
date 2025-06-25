package toy.test.holidaymanager.holiday.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SaveRecentGlobalHolidaysUseCase {
    void execute() throws JsonProcessingException;
}
