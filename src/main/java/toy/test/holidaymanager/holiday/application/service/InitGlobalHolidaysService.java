package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.InitGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.SaveRecentGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;

@RequiredArgsConstructor
@Service
public class InitGlobalHolidaysService implements InitGlobalHolidaysUseCase {
    private final HolidayRepository repository;
    private final SaveRecentGlobalHolidaysUseCase useCase;

    @Override
    public void execute() {
        if (isInitialized()) {
            return;
        }
        useCase.execute();
    }

    private boolean isInitialized() {
        return repository.count() > 0;
    }
}
