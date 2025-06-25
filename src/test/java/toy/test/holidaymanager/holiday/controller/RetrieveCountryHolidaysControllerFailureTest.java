package toy.test.holidaymanager.holiday.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.config.IntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@IntegrationTest
public class RetrieveCountryHolidaysControllerFailureTest {
    @Autowired
    private MockMvc mockMvc;

    @Transactional
    @Test
    public void 조회_실패_toMonth_오류() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer errorTo = 100;

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageOneBased))
                        .param("size", String.valueOf(maxSize))
                        .param("to", String.valueOf(errorTo)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void 조회_실패_fromMonth_오류() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer errorFrom = 100;

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageOneBased))
                        .param("size", String.valueOf(maxSize))
                        .param("from", String.valueOf(errorFrom)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void 조회_실패_fromMonth_toMonth_오류() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final Integer from = 5;
        final Integer errorTo = from - 1;

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageOneBased))
                        .param("size", String.valueOf(maxSize))
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(errorTo)))
                .andExpect(status().isBadRequest());
    }

    @Transactional
    @Test
    public void 조회_실패_types_오류() throws Exception {
        final int year = 2025;
        final String countryCode = "KR";
        final int pageOneBased = 1;
        final int maxSize = 10;
        final String types = "없는_코드";

        mockMvc.perform(get("/api/holidays/" + year + "/" + countryCode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", String.valueOf(pageOneBased))
                        .param("size", String.valueOf(maxSize))
                        .param("types", types))
                .andExpect(status().isBadRequest());
    }
}
