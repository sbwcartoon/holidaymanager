package toy.test.holidaymanager.holiday;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerClientException;
import toy.test.holidaymanager.holiday.application.port.in.FetchCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.config.IntegrationTest;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@IntegrationTest
public class FetchCountryHolidaysTest {
    @Autowired
    private FetchCountryHolidaysUseCase fetchCountryHolidaysUseCase;

    @Test
    public void 공휴일_fetch_성공() {
        List<Holiday> result = fetchCountryHolidaysUseCase.fetch(2025, "KR");
        assertThat(result).isNotEmpty();
    }

    @Test
    public void 공휴일_fetch_실패_오류연도_조회() {
        final int errorYear = -1000;
        assertThatThrownBy(() -> fetchCountryHolidaysUseCase.fetch(errorYear, "KR"))
                .isInstanceOf(NagerClientException.class);
    }

    @Test
    public void 공휴일_fetch_실패_오류국가코드_조회() {
        final String errorCountryCode = "오류국가코드";
        assertThatThrownBy(() -> fetchCountryHolidaysUseCase.fetch(2025, errorCountryCode))
                .isInstanceOf(NagerClientException.class);
    }
}
