package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.RemoveGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;

@RequiredArgsConstructor
@Service
public class RemoveGlobalHolidaysService implements RemoveGlobalHolidaysUseCase {
    private final HolidayRepository holidayRepository;

    @Override
    public void execute(final int year) {
        holidayRepository.deleteAllByYear(new HolidayYear(year));
    }
}
