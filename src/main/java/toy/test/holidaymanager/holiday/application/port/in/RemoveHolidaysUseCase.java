package toy.test.holidaymanager.holiday.application.port.in;

import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;

public interface RemoveHolidaysUseCase {
    void execute(final RemoveCommand command);
}
