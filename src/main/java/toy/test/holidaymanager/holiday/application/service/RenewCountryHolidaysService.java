package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RenewCountryHolidaysService implements RenewCountryHolidaysUseCase {
    private final FetchCountryHolidaysUseCase fetchCountryHolidaysUseCase;
    private final RemoveCountryHolidaysService removeHolidaysService;
    private final SaveHolidaysService saveHolidaysService;

    @Transactional
    @Override
    public void execute(final RenewCommand command) {
        final List<Holiday> data = fetchCountryHolidaysUseCase.fetch(
                command.year().value(),
                command.countryCode().value()
        );
        removeHolidaysService.execute(new RemoveCommand(command.year(), command.countryCode()));
        saveHolidaysService.execute(data);
    }
}
