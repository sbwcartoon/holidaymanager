package toy.test.holidaymanager.holiday.adapter.out.nager;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerHolidayClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.dto.NagerHolidayResponse;
import toy.test.holidaymanager.holiday.application.port.out.DateSourceRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NagerDateAdapter implements DateSourceRepository {
    private final NagerHolidayClient nagerHolidayClient;

    @Override
    public List<Holiday> findByYearAndCountryCode(final int year, final String countryCode) throws JsonProcessingException {
        List<NagerHolidayResponse> holidays = nagerHolidayClient.fetch(year, countryCode);
        return holidays.stream().map(NagerHolidayResponse::toDomain).toList();
    }
}
