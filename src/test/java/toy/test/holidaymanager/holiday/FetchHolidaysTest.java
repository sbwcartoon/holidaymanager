package toy.test.holidaymanager.holiday;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import toy.test.holidaymanager.holiday.adapter.out.nager.NagerDateAdapter;
import toy.test.holidaymanager.holiday.adapter.out.nager.client.NagerHolidayClient;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerFetchIllegalArgumentException;
import toy.test.holidaymanager.holiday.adapter.out.nager.exception.NagerHolidayFetchException;
import toy.test.holidaymanager.holiday.application.port.in.FetchHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.out.DateSourceRepository;
import toy.test.holidaymanager.holiday.application.service.FetchHolidaysService;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FetchHolidaysTest {
    private final NagerHolidayClient client = new NagerHolidayClient(new ObjectMapper());
    private final DateSourceRepository repository = new NagerDateAdapter(client);
    private final FetchHolidaysUseCase fetchHolidaysUseCase = new FetchHolidaysService(repository);

    @Test
    public void 공휴일_fetch_성공() throws JsonProcessingException {
        List<Holiday> result = fetchHolidaysUseCase.fetch(2025, "KR");
        assertThat(result).isNotEmpty();
    }

    @Test
    public void 공휴일_fetch_실패_오류연도_조회() {
        final int errorYear = -1000;
        assertThatThrownBy(() -> fetchHolidaysUseCase.fetch(errorYear, "KR"))
                .isInstanceOf(NagerFetchIllegalArgumentException.class);
    }

    @Test
    public void 공휴일_fetch_실패_오류국가코드_조회() {
        final String errorCountryCode = "오류국가코드";
        assertThatThrownBy(() -> fetchHolidaysUseCase.fetch(2025, errorCountryCode))
                .isInstanceOf(NagerHolidayFetchException.class);
    }
}
