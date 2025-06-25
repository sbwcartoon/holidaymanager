package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.BeforeAll;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class RetrieveCountryHolidaysTest {
    @Autowired
    private RetrieveHolidaysUseCase useCase;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    private static final List<HolidayJpaEntity> testData = new ArrayList<>();

    @BeforeAll
    public static void setUpClass() {
        final HolidayJpaEntity e = HolidayJpaEntity.builder()
                .countryCode("US")
                .date(LocalDate.of(2025, 1, 1))
                .localName("New Year's Day")
                .name("New Year's Day")
                .global(true)
                .launchYear(null)
                .build();
        e.addHolidayTypeCode(HolidayTypeCode.Public);
        testData.add(e);

        final HolidayJpaEntity e0 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 1, 30))
                .localName("설날")
                .name("Lunar New Year")
                .global(false)
                .launchYear(null)
                .build();
        e0.addHolidayTypeCode(HolidayTypeCode.Public);
        e0.addHolidayCounty("KR-11");
        testData.add(e0);

        final HolidayJpaEntity e1 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 3, 1))
                .localName("3·1절")
                .name("Independence Movement Day")
                .global(false)
                .launchYear(null)
                .build();
        e1.addHolidayTypeCode(HolidayTypeCode.Public);
        e1.addHolidayTypeCode(HolidayTypeCode.School);
        e1.addHolidayCounty("KR-11");
        e1.addHolidayCounty("KR-12");
        testData.add(e1);

        final HolidayJpaEntity e2 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 5, 5))
                .localName("어린이날")
                .name("Children's Day")
                .global(true)
                .launchYear(null)
                .build();
        e2.addHolidayTypeCode(HolidayTypeCode.Public);
        e2.addHolidayTypeCode(HolidayTypeCode.School);
        testData.add(e2);

        final HolidayJpaEntity e3 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 5, 5))
                .localName("부처님 오신 날")
                .name("Buddha's Birthday")
                .global(true)
                .launchYear(null)
                .build();
        e3.addHolidayTypeCode(HolidayTypeCode.Public);
        e3.addHolidayTypeCode(HolidayTypeCode.Observance);
        testData.add(e3);
    }

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
