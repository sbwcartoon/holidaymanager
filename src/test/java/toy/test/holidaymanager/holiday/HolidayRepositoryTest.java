package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.config.JpaTest;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JpaTest
public class HolidayRepositoryTest {
    @Autowired
    private HolidayJpaRepository jpaRepository;

    @Transactional
    @Test
    public void 저장_조회_성공() {
        final HolidayJpaEntity entity = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 5, 5))
                .localName("어린이날")
                .name("Children's Day")
                .global(true)
                .launchYear(null)
                .build();
        entity.addHolidayTypeCode(HolidayTypeCode.Public);

        jpaRepository.saveAll(List.of(entity));

        final List<HolidayJpaEntity> saved = jpaRepository.findAll();
        assertThat(saved).size().isEqualTo(1);
        assertThat(saved.getFirst()).isEqualTo(entity);
    }
}
