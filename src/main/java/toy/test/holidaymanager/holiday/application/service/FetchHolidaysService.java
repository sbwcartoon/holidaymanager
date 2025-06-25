package toy.test.holidaymanager.holiday.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.FetchHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.DateSourceRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;
import toy.test.holidaymanager.holiday.domain.vo.HolidayYear;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FetchHolidaysService implements FetchHolidaysUseCase {
    private final DateSourceRepository dateSourceRepository;

    @Override
    public List<Holiday> fetch(final int year, final String countryCode) throws JsonProcessingException {
        return dateSourceRepository.findByYearAndCountryCode(
                new HolidayYear(year),
                new CountryCode(countryCode)
        );
    }
}
