package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.SaveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SaveHolidaysService implements SaveHolidaysUseCase {
    private final HolidayRepository holidayRepository;

    @Override
    public void execute(final List<Holiday> holidays) {
        holidayRepository.saveAll(holidays);
    }
}
