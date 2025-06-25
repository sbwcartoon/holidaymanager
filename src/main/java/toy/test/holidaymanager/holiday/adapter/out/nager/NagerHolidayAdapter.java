package toy.test.holidaymanager.holiday.adapter.out.nager;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerHolidayClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.dto.NagerHolidayResponse;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.application.port.out.HolidaySourceRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NagerHolidayAdapter implements HolidaySourceRepository {
    private final NagerHolidayClient nagerHolidayClient;

    @Override
    public List<Holiday> findByYearAndCountryCode(
            final HolidayYear year,
            final CountryCode countryCode
    ) throws JsonProcessingException {
        List<NagerHolidayResponse> holidays = nagerHolidayClient.fetch(year.value(), countryCode.value());
        return holidays.stream().map(NagerHolidayResponse::toDomain).toList();
    }
}
