package toy.test.holidaymanager.holiday.adapter.out.nager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerCountryClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.dto.NagerCountryResponse;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class NagerCountryAdapter implements CountrySourceRepository {
    private final NagerCountryClient nagerHolidayClient;

    @Override
    public List<CountryCode> findAll() {
        final List<NagerCountryResponse> holidays = nagerHolidayClient.fetchAll();
        return holidays.stream().map(NagerCountryResponse::toDomain).toList();
    }
}
