package toy.test.holidaymanager.holiday.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.RenewGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewNewYearGlobalHolidaysUseCase;

import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class RenewNewYearGlobalHolidaysService implements RenewNewYearGlobalHolidaysUseCase {
    private final RenewGlobalHolidaysUseCase renewGlobalHolidaysUseCase;

    @Transactional
    @Override
    public void execute() throws JsonProcessingException {
        List<Integer> years = getRecent2Years();
        for (int year : years) {
            renewGlobalHolidaysUseCase.execute(year);
        }
    }

    private List<Integer> getRecent2Years() {
        int currentYear = Year.now().getValue();

        return IntStream.rangeClosed(currentYear - 1, currentYear)
                .boxed()
                .toList();
    }
}
