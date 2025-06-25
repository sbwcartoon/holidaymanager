package toy.test.holidaymanager.holiday.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.FetchHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RenewHolidaysService implements RenewHolidaysUseCase {
    private final FetchHolidaysUseCase fetchHolidaysUseCase;
    private final RemoveHolidaysService removeHolidaysService;
    private final SaveHolidaysService saveHolidaysService;

    @Transactional
    @Override
    public void execute(final RenewCommand command) throws JsonProcessingException {
        List<Holiday> data = fetchHolidaysUseCase.fetch(
                command.year().value(),
                command.countryCode().value()
        );
        removeHolidaysService.execute(new RemoveCommand(command.year(), command.countryCode()));
        saveHolidaysService.execute(data);
    }
}
