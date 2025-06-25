package toy.test.holidaymanager.holiday.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface InitGlobalHolidaysUseCase {
    void execute() throws JsonProcessingException;
}
