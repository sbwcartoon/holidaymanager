package toy.test.holidaymanager.holiday.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.SaveRecentGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.time.Year;
import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class SaveRecentGlobalHolidaysService implements SaveRecentGlobalHolidaysUseCase {
    private final FetchCountryHolidaysUseCase fetchCountryHolidaysUseCase;
    private final SaveHolidaysService saveHolidaysService;
    private final CountrySourceRepository repository;

    @Transactional
    @Override
    public void execute() throws JsonProcessingException {
        List<CountryCode> countryCodes = repository.findAll();
        List<Integer> years = getRecent5Years();
        for (int year : years) {
            for (CountryCode countryCode : countryCodes) {
                List<Holiday> data = fetchCountryHolidaysUseCase.fetch(year, countryCode.value());
                saveHolidaysService.execute(data);
            }
        }
    }

    private List<Integer> getRecent5Years() {
        int currentYear = Year.now().getValue();

        return IntStream.rangeClosed(currentYear - 4, currentYear)
                .boxed()
                .toList();
    }
}
