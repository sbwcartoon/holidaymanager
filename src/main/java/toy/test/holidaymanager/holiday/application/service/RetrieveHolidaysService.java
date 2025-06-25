package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.RetrieveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

@RequiredArgsConstructor
@Service
public class RetrieveHolidaysService implements RetrieveHolidaysUseCase {
    private final HolidayRepository holidayRepository;

    @Override
    public Page<Holiday> execute(final RetrieveFilterCommand command, final Pageable pageable) {
        return holidayRepository.findAllByCondition(command, pageable);
    }
}
