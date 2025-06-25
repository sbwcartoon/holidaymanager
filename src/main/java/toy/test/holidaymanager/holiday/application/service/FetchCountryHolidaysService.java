package toy.test.holidaymanager.holiday.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.application.port.out.HolidaySourceRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FetchCountryHolidaysService implements FetchCountryHolidaysUseCase {
    private final HolidaySourceRepository holidaySourceRepository;

    @Override
    public List<Holiday> fetch(final int year, final String countryCode) throws JsonProcessingException {
        return holidaySourceRepository.findByYearAndCountryCode(
                new HolidayYear(year),
                new CountryCode(countryCode)
        );
    }
}
