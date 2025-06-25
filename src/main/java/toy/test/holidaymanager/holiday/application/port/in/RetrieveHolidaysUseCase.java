package toy.test.holidaymanager.holiday.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

public interface RetrieveHolidaysUseCase {
    Page<Holiday> execute(final RetrieveFilterCommand command, final Pageable pageable);
}
