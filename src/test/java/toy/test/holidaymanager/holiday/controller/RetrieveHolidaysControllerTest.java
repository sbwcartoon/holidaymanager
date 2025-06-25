package toy.test.holidaymanager.holiday.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.in.dto.PageResponse;
import toy.test.holidaymanager.holiday.adapter.in.dto.RetrievedHoliday;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@IntegrationTest
public class RetrieveHolidaysControllerTest {
    @Autowired
    private MockMvc mockMvc;

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

        final HolidayJpaEntity e4 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2026, 5, 5))
                .localName("어린이날")
                .name("Children's Day")
                .global(true)
                .launchYear(null)
                .build();
        e4.addHolidayTypeCode(HolidayTypeCode.Public);
        e4.addHolidayTypeCode(HolidayTypeCode.School);
        testData.add(e4);
    }

    @BeforeEach
    public void setUp() {
        jpaRepository.saveAll(testData);
    }

    @Transactional
    @Test
    public void 조회_페이지네이션_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 2;

        final List<RetrievedHoliday> expectedContentTotal = testData.stream()
                .filter(it -> it.getDate().getYear() == year)
                .filter(it -> it.getCountryCode().equals(countryCode))
                .map(HolidayJpaMapper::toDomain)
                .map(RetrievedHoliday::from)
                .toList();

        final List<RetrievedHoliday> expectedContent = expectedContentTotal.stream().limit(maxSize).toList();

        final PageImpl<RetrievedHoliday> result = new PageImpl<>(expectedContent, PageRequest.of(pageOneBased - 1, maxSize), expectedContentTotal.size());
        final PageResponse<RetrievedHoliday> response = PageResponse.from(result);

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageOneBased))
                        .param("size", String.valueOf(maxSize)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Transactional
    @Test
    public void 조회_fromMonth_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer from = 5;

        final List<RetrievedHoliday> expectedContentTotal = testData.stream()
                .filter(it -> it.getDate().getYear() == year)
                .filter(it -> it.getCountryCode().equals(countryCode))
                .filter(it -> it.getDate().getMonthValue() >= from)
                .map(HolidayJpaMapper::toDomain)
                .map(RetrievedHoliday::from)
                .toList();

        final List<RetrievedHoliday> expectedContent = expectedContentTotal.stream().limit(maxSize).toList();

        final PageImpl<RetrievedHoliday> result = new PageImpl<>(expectedContent, PageRequest.of(pageOneBased - 1, maxSize), expectedContentTotal.size());
        final PageResponse<RetrievedHoliday> response = PageResponse.from(result);

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("from", String.valueOf(from)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Transactional
    @Test
    public void 조회_toMonth_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer to = 3;

        final List<RetrievedHoliday> expectedContentTotal = testData.stream()
                .filter(it -> it.getDate().getYear() == year)
                .filter(it -> it.getCountryCode().equals(countryCode))
                .filter(it -> it.getDate().getMonthValue() <= to)
                .map(HolidayJpaMapper::toDomain)
                .map(RetrievedHoliday::from)
                .toList();

        final List<RetrievedHoliday> expectedContent = expectedContentTotal.stream().limit(maxSize).toList();

        final PageImpl<RetrievedHoliday> result = new PageImpl<>(expectedContent, PageRequest.of(pageOneBased - 1, maxSize), expectedContentTotal.size());
        final PageResponse<RetrievedHoliday> response = PageResponse.from(result);

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("to", String.valueOf(to)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Transactional
    @Test
    public void 조회_fromMonth_toMonth_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer from = 3;
        final Integer to = 5;

        final List<RetrievedHoliday> expectedContentTotal = testData.stream()
                .filter(it -> it.getDate().getYear() == year)
                .filter(it -> it.getCountryCode().equals(countryCode))
                .filter(it -> it.getDate().getMonthValue() >= from)
                .filter(it -> it.getDate().getMonthValue() <= to)
                .map(HolidayJpaMapper::toDomain)
                .map(RetrievedHoliday::from)
                .toList();

        final List<RetrievedHoliday> expectedContent = expectedContentTotal.stream().limit(maxSize).toList();

        final PageImpl<RetrievedHoliday> result = new PageImpl<>(expectedContent, PageRequest.of(pageOneBased - 1, maxSize), expectedContentTotal.size());
        final PageResponse<RetrievedHoliday> response = PageResponse.from(result);

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Transactional
    @Test
    public void 조회_types_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final List<HolidayTypeCode> typeCodes = List.of(HolidayTypeCode.School, HolidayTypeCode.Observance);

        final List<RetrievedHoliday> expectedContentTotal = testData.stream()
                .filter(it -> it.getDate().getYear() == year)
                .filter(it -> it.getCountryCode().equals(countryCode))
                .filter(it -> it.getTypes().stream()
                        .anyMatch(it2 -> typeCodes.contains(it2.getCode())))
                .map(HolidayJpaMapper::toDomain)
                .map(RetrievedHoliday::from)
                .toList();

        final List<RetrievedHoliday> expectedContent = expectedContentTotal.stream().limit(maxSize).toList();

        final PageImpl<RetrievedHoliday> result = new PageImpl<>(expectedContent, PageRequest.of(pageOneBased - 1, maxSize), expectedContentTotal.size());
        final PageResponse<RetrievedHoliday> response = PageResponse.from(result);

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("types", typeCodes.stream()
                                .map(HolidayTypeCode::name)
                                .toArray(String[]::new)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }

    @Transactional
    @Test
    public void 조회_모든_조건_성공() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer from = 3;
        final Integer to = 5;
        final List<HolidayTypeCode> typeCodes = List.of(HolidayTypeCode.School, HolidayTypeCode.Observance);

        final List<RetrievedHoliday> expectedContentTotal = testData.stream()
                .filter(it -> it.getDate().getYear() == year)
                .filter(it -> it.getCountryCode().equals(countryCode))
                .filter(it -> it.getDate().getMonthValue() >= from)
                .filter(it -> it.getDate().getMonthValue() <= to)
                .filter(it -> it.getTypes().stream()
                        .anyMatch(it2 -> typeCodes.contains(it2.getCode())))
                .map(HolidayJpaMapper::toDomain)
                .map(RetrievedHoliday::from)
                .toList();

        final List<RetrievedHoliday> expectedContent = expectedContentTotal.stream().limit(maxSize).toList();

        final PageImpl<RetrievedHoliday> result = new PageImpl<>(expectedContent, PageRequest.of(pageOneBased - 1, maxSize), expectedContentTotal.size());
        final PageResponse<RetrievedHoliday> response = PageResponse.from(result);

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageOneBased))
                        .param("size", String.valueOf(maxSize))
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .param("types", typeCodes.stream()
                                .map(HolidayTypeCode::name)
                                .toArray(String[]::new)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
    }
}
