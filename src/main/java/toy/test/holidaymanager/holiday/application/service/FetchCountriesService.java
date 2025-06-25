package toy.test.holidaymanager.holiday.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountriesUseCase;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FetchCountriesService implements FetchCountriesUseCase {
    private final CountrySourceRepository countrySourceRepository;

    @Override
    public List<CountryCode> fetch() throws JsonProcessingException {
        return countrySourceRepository.findAll();
    }
}
