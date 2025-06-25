package toy.test.holidaymanager.holiday.application.port.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;

public interface RenewCountryHolidaysUseCase {
    void execute(final RenewCommand command) throws JsonProcessingException;
}
