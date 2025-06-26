package toy.test.holidaymanager.holiday.adapter.in.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import toy.test.holidaymanager.holiday.application.port.in.RenewNewYearGlobalHolidaysUseCase;

@RequiredArgsConstructor
@Component
public class HolidayNewYearInitializer {
    private final RenewNewYearGlobalHolidaysUseCase useCase;

    @Scheduled(cron = "0 0 1 2 1 *", zone = "Asia/Seoul")
    public void execute() {
        useCase.execute();
    }
}
