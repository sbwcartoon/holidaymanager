package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.in.RetrieveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class RetrieveCountryHolidaysTest {
    @Autowired
    private RetrieveHolidaysUseCase useCase;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    private final List<HolidayJpaEntity> testData = HolidayTestFixture.generateHolidayJpaEntities();

    @BeforeEach
    public void setUp() {
        jpaRepository.saveAll(testData);
    }

    @Transactional
    @Test
    public void 조회_페이지네이션_성공() {
        final int maxSize = 2;
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> it.getDate().getYear() == 2025)
                .filter(it -> it.getCountryCode().equals("KR"))
                .toList();

        final RetrieveFilterCommand command = RetrieveFilterCommand.from(2025, "KR", null, null, null);
        Page<Holiday> result = useCase.execute(command, PageRequest.of(0, maxSize));

        assertThat(result.getTotalElements()).isEqualTo(expectedTotalElements.size());
        assertThat(result.getContent().size()).isEqualTo(expectedTotalElements.stream().limit(maxSize).count());
    }

    @Transactional
    @Test
    public void 조회_fromMonth_성공() {
        final int maxSize = 1000;
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> it.getDate().getYear() == 2025)
                .filter(it -> it.getCountryCode().equals("KR"))
                .filter(it -> it.getDate().getMonthValue() >= 5)
                .toList();

        final RetrieveFilterCommand command = RetrieveFilterCommand.from(2025, "KR", 5, null, null);
        Page<Holiday> result = useCase.execute(command, PageRequest.of(0, maxSize));

        assertThat(result.getTotalElements()).isEqualTo(expectedTotalElements.size());
        assertThat(result.getContent().size()).isEqualTo(expectedTotalElements.stream().limit(maxSize).count());
    }

    @Transactional
    @Test
    public void 조회_toMonth_성공() {
        final int maxSize = 1000;
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> it.getDate().getYear() == 2025)
                .filter(it -> it.getCountryCode().equals("KR"))
                .filter(it -> it.getDate().getMonthValue() <= 3)
                .toList();

        final RetrieveFilterCommand command = RetrieveFilterCommand.from(2025, "KR", null, 3, null);
        Page<Holiday> result = useCase.execute(command, PageRequest.of(0, maxSize));

        assertThat(result.getTotalElements()).isEqualTo(expectedTotalElements.size());
        assertThat(result.getContent().size()).isEqualTo(expectedTotalElements.stream().limit(maxSize).count());
    }

    @Transactional
    @Test
    public void 조회_fromMonth_toMonth_성공() {
        final int maxSize = 1000;
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> it.getDate().getYear() == 2025)
                .filter(it -> it.getCountryCode().equals("KR"))
                .filter(it -> it.getDate().getMonthValue() >= 3)
                .filter(it -> it.getDate().getMonthValue() <= 5)
                .toList();

        final RetrieveFilterCommand command = RetrieveFilterCommand.from(2025, "KR", 3, 5, null);
        Page<Holiday> result = useCase.execute(command, PageRequest.of(0, maxSize));

        assertThat(result.getTotalElements()).isEqualTo(expectedTotalElements.size());
        assertThat(result.getContent().size()).isEqualTo(expectedTotalElements.stream().limit(maxSize).count());
    }

    @Transactional
    @Test
    public void 조회_types_성공() {
        final List<HolidayTypeCode> typeCodes = List.of(HolidayTypeCode.School, HolidayTypeCode.Observance);
        final int maxSize = 1000;
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> it.getDate().getYear() == 2025)
                .filter(it -> it.getCountryCode().equals("KR"))
                .filter(it -> it.getTypes().stream()
                        .anyMatch(it2 -> typeCodes.contains(it2.getCode())))
                .toList();

        final RetrieveFilterCommand command = RetrieveFilterCommand.from(
                2025, "KR", null, null, typeCodes.stream().map(HolidayTypeCode::name).toList());
        Page<Holiday> result = useCase.execute(command, PageRequest.of(0, maxSize));

        assertThat(result.getTotalElements()).isEqualTo(expectedTotalElements.size());
        assertThat(result.getContent().size()).isEqualTo(expectedTotalElements.stream().limit(maxSize).count());
    }

    @Transactional
    @Test
    public void 조회_모든_조건_성공() {
        final List<HolidayTypeCode> typeCodes = List.of(HolidayTypeCode.School, HolidayTypeCode.Observance);
        final int maxSize = 2;
        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> it.getDate().getYear() == 2025)
                .filter(it -> it.getCountryCode().equals("KR"))
                .filter(it -> it.getDate().getMonthValue() >= 3)
                .filter(it -> it.getDate().getMonthValue() <= 5)
                .filter(it -> it.getTypes().stream()
                        .anyMatch(it2 -> typeCodes.contains(it2.getCode())))
                .toList();

        final RetrieveFilterCommand command = RetrieveFilterCommand.from(
                2025, "KR", 3, 5, typeCodes.stream().map(HolidayTypeCode::name).toList());
        Page<Holiday> result = useCase.execute(command, PageRequest.of(0, maxSize));

        assertThat(result.getTotalElements()).isEqualTo(expectedTotalElements.size());
        assertThat(result.getContent().size()).isEqualTo(expectedTotalElements.stream().limit(maxSize).count());
    }
}
