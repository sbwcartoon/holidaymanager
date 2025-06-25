package toy.test.holidaymanager.holiday.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.HolidayTestFixture;
import toy.test.holidaymanager.holiday.adapter.in.dto.PageResponse;
import toy.test.holidaymanager.holiday.adapter.in.dto.RetrievedHoliday;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

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

    private final List<HolidayJpaEntity> testData = HolidayTestFixture.generateHolidayJpaEntities();

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
