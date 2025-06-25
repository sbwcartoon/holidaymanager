package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.in.SaveHolidaysUseCase;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class SaveHolidaysTest {
    @Autowired
    private SaveHolidaysUseCase useCase;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    @Transactional
    @Test
    public void 저장_성공() {
        final Holiday holiday = Holiday.builder()
                .countryCode(new CountryCode("KR"))
                .date(LocalDate.of(2025, 5, 5))
                .localName(new HolidayLocalName("어린이날"))
                .name(new HolidayName("Children's Day"))
                .global(new Global(true))
                .launchYear(null)
                .counties(Set.of(new HolidayCounty("KR-11")))
                .types(Set.of(HolidayTypeCode.Public))
                .build();

        useCase.execute(List.of(holiday));

        List<HolidayJpaEntity> saved = jpaRepository.findAll();
        assertThat(saved).size().isEqualTo(1);
        assertThat(HolidayJpaMapper.toDomain(saved.getFirst())).isEqualTo(holiday);
    }
}
