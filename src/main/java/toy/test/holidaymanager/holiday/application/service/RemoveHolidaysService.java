package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.RemoveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;

@RequiredArgsConstructor
@Service
public class RemoveHolidaysService implements RemoveHolidaysUseCase {
    private final HolidayRepository holidayRepository;

    @Override
    public void execute(final RemoveCommand command) {
        holidayRepository.deleteAllByCondition(command);
    }
}
