package toy.test.holidaymanager.holiday.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.HolidayTestFixture;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.config.IntegrationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@IntegrationTest
public class RemoveHolidaysControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HolidayJpaRepository jpaRepository;

    private final List<HolidayJpaEntity> testData = HolidayTestFixture.generateHolidayJpaEntities();

    @BeforeEach
    public void setUp() {
        jpaRepository.saveAll(testData);
    }

    @Transactional
    @Test
    public void 삭제_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";

        final List<HolidayJpaEntity> expectedTotalElements = testData.stream()
                .filter(it -> !(it.getDate().getYear() == year && it.getCountryCode().equals(countryCode)))
                .toList();

        mockMvc.perform(delete("/api/holidays/" + year + "/" + countryCode))
                .andExpect(status().isOk());

        List<HolidayJpaEntity> saved = jpaRepository.findAll();
        assertThat(saved).isEqualTo(expectedTotalElements);
    }
}
