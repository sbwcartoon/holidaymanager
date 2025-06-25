package toy.test.holidaymanager.holiday.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

public interface RetrieveHolidaysUseCase {

    Page<Holiday> execute(
            final int year,
            final String countryCode,
            final Integer fromMonth,
            final Integer toMonth,
            final List<String> types,
            final Pageable pageable
    );
}
