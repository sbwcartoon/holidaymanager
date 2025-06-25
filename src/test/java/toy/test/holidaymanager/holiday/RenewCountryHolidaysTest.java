package toy.test.holidaymanager.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.in.FetchHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class RenewCountryHolidaysTest {
    @Autowired
    private FetchHolidaysUseCase fetchHolidaysUseCase;

    @Autowired
    private RenewHolidaysUseCase renewHolidaysUseCase;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    private final List<HolidayJpaEntity> testData = HolidayTestFixture.generateHolidayJpaEntities();

    @BeforeEach
    public void setUp() {
        jpaRepository.saveAll(testData);
    }

    @Transactional
    @Test
    public void 재동기화_성공() throws JsonProcessingException {
        final HolidayJpaEntity first = testData.getFirst();
        final int year = first.getDate().getYear();
        final String countryCode = first.getCountryCode();

        renewHolidaysUseCase.execute(RenewCommand.from(year, countryCode));

        List<HolidayJpaEntity> saved = jpaRepository.findAllByYearAndCountryCode(year, countryCode);
        List<Holiday> savedHolidays = saved.stream().map(HolidayJpaMapper::toDomain).toList();

        List<Holiday> fetched = fetchHolidaysUseCase.fetch(year, countryCode);

        assertThat(savedHolidays)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(fetched);
    }
}
