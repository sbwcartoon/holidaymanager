package toy.test.holidaymanager.holiday.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.application.port.in.RenewCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewGlobalHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;
import toy.test.holidaymanager.holiday.application.port.out.CountrySourceRepository;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RenewGlobalHolidaysService implements RenewGlobalHolidaysUseCase {
    private final CountrySourceRepository repository;
    private final RenewCountryHolidaysUseCase renewCountryHolidaysUseCase;

    @Transactional
    @Override
    public void execute(final int year) {
        final List<CountryCode> countryCodes = repository.findAll();
        for (CountryCode countryCode : countryCodes) {
            renewCountryHolidaysUseCase.execute(RenewCommand.from(year, countryCode.value()));
        }
    }
}
