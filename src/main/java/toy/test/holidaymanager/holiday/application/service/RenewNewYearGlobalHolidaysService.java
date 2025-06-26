package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.FetchGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RemoveGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewNewYearGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class RenewNewYearGlobalHolidaysService implements RenewNewYearGlobalHolidaysUseCase {
    private final SaveHolidaysService saveHolidaysService;
    private final FetchGlobalHolidaysUseCase fetchGlobalHolidaysUseCase;
    private final RemoveGlobalHolidaysUseCase removeGlobalHolidaysUseCase;

    @Transactional
    @Override
    public void execute() {
        final List<Integer> years = getRecent2Years();
        for (int year : years) {
            removeGlobalHolidaysUseCase.execute(year);

            final List<Holiday> data = fetchGlobalHolidaysUseCase.fetch(year);
            saveHolidaysService.execute(data);
        }
    }

    private List<Integer> getRecent2Years() {
        int currentYear = Year.now().getValue();

        return IntStream.rangeClosed(currentYear - 1, currentYear)
                .boxed()
                .toList();
    }
}
