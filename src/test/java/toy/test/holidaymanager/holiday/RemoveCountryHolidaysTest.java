package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.in.RemoveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.config.IntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class RemoveCountryHolidaysTest {
    @Autowired
    private RemoveHolidaysUseCase useCase;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    private final List<HolidayJpaEntity> testData = HolidayTestFixture.generateHolidayJpaEntities();

    @BeforeEach
    public void setUp() {
        jpaRepository.saveAll(testData);
    }

    @Transactional
    @Test
    public void 삭제_성공() {
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> !(it.getDate().getYear() == 2025 && it.getCountryCode().equals("KR")))
                .toList();

        useCase.execute(RemoveCommand.from(2025, "KR"));

        List<HolidayJpaEntity> saved = jpaRepository.findAll();
        assertThat(saved).isEqualTo(expectedTotalElements);
    }
}
