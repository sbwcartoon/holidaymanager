package toy.test.holidaymanager.holiday.adapter.in.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import toy.test.holidaymanager.holiday.application.port.in.InitGlobalHolidaysUseCase;

@RequiredArgsConstructor
@Profile("!test")
@Component
public class HolidayInitializer {
    private final InitGlobalHolidaysUseCase useCase;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        useCase.execute();
    }
}
