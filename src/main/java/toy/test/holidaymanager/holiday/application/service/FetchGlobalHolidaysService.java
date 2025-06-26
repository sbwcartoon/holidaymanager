package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.FetchGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.application.port.out.HolidaySourceRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FetchGlobalHolidaysService implements FetchGlobalHolidaysUseCase {
    private final HolidaySourceRepository holidaySourceRepository;
    private final CountrySourceRepository repository;

    @Override
    public List<Holiday> fetch(final int year) {
        List<CountryCode> countryCodes = repository.findAll();
        return holidaySourceRepository.findByYearAndCountryCodes(new HolidayYear(year), countryCodes);
    }
}
