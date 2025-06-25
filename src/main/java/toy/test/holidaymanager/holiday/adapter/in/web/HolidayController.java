package toy.test.holidaymanager.holiday.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import toy.test.holidaymanager.holiday.adapter.in.dto.PageResponse;
import toy.test.holidaymanager.holiday.adapter.in.dto.RetrievedHoliday;
import toy.test.holidaymanager.holiday.application.port.in.RetrieveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    private final RetrieveHolidaysUseCase retrieveHolidaysUseCase;

    @GetMapping("/{year}/{countryCode}")
    public PageResponse<RetrievedHoliday> getHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode,
            @RequestParam(required = false) final Integer from,
            @RequestParam(required = false) final Integer to,
            @RequestParam(required = false) final List<String> types,
            @PageableDefault(size = 10) final Pageable pageable
    ) {
        Page<Holiday> holidays = retrieveHolidaysUseCase.execute(
                RetrieveFilterCommand.from(year, countryCode, from, to, types),
                pageable
        );

        Page<RetrievedHoliday> retrievedHolidays = holidays.map(RetrievedHoliday::from);
        return PageResponse.from(retrievedHolidays);
    }
}
