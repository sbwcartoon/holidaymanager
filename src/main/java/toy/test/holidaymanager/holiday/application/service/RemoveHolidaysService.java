package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.RemoveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

@RequiredArgsConstructor
@Service
public class RemoveHolidaysService implements RemoveHolidaysUseCase {
    private final HolidayRepository holidayRepository;

    @Override
    public void execute(final int year, final String countryCode) {
        holidayRepository.deleteByYearAndCountryCode(
                new HolidayYear(year),
                new CountryCode(countryCode)
        );
    }
}
