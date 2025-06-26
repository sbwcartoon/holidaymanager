package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.FetchGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.SaveRecentGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class SaveRecentGlobalHolidaysService implements SaveRecentGlobalHolidaysUseCase {
    private final SaveHolidaysService saveHolidaysService;
    private final FetchGlobalHolidaysUseCase fetchGlobalHolidaysUseCase;

    @Transactional
    @Override
    public void execute() {
        final List<Integer> years = getRecent5Years();
        for (int year : years) {
            final List<Holiday> data = fetchGlobalHolidaysUseCase.fetch(year);
            saveHolidaysService.execute(data);
        }
    }

    private List<Integer> getRecent5Years() {
        final int currentYear = Year.now().getValue();

        return IntStream.rangeClosed(currentYear - 4, currentYear)
                .boxed()
                .toList();
    }
}
