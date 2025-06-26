package toy.test.holidaymanager.holiday.adapter.out.nager;

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
    ) {
        final List<NagerHolidayResponse> holidays = nagerHolidayClient.fetchAll(
                List.of(year.value()),
                List.of(countryCode.value())
        );
        return holidays.stream().map(NagerHolidayResponse::toDomain).toList();
    }

    @Override
    public List<Holiday> findByYearAndCountryCodes(
            final HolidayYear year,
            final List<CountryCode> countryCode
    ) {
        final List<NagerHolidayResponse> holidays = nagerHolidayClient.fetchAll(
                List.of(year.value()),
                countryCode.stream().map(CountryCode::value).toList()
        );
        return holidays.stream().map(NagerHolidayResponse::toDomain).toList();
    }
}
