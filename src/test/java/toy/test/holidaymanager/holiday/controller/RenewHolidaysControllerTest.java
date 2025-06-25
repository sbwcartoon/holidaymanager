package toy.test.holidaymanager.holiday.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.HolidayTestFixture;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.in.FetchHolidaysUseCase;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@IntegrationTest
public class RenewHolidaysControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FetchHolidaysUseCase fetchHolidaysUseCase;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    private final List<HolidayJpaEntity> testData = HolidayTestFixture.generateHolidayJpaEntities();

    @BeforeEach
    public void setUp() {
        jpaRepository.saveAll(testData);
    }

    @Transactional
    @Test
    public void 재동기화_성공() throws Exception {
        final HolidayJpaEntity first = testData.getFirst();
        final int year = first.getDate().getYear();
        final String countryCode = first.getCountryCode();

        mockMvc.perform(post("/api/holidays/" + year + "/" + countryCode + "/refresh"))
                .andExpect(status().isOk());

        List<HolidayJpaEntity> saved = jpaRepository.findAllByYearAndCountryCode(year, countryCode);
        List<Holiday> savedHolidays = saved.stream().map(HolidayJpaMapper::toDomain).toList();

        List<Holiday> fetched = fetchHolidaysUseCase.fetch(year, countryCode);

        assertThat(savedHolidays)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .containsExactlyInAnyOrderElementsOf(fetched);
    }
}
