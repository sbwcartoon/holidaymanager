package toy.test.holidaymanager.holiday.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import toy.test.holidaymanager.holiday.adapter.in.dto.PageResponse;
import toy.test.holidaymanager.holiday.adapter.in.dto.RetrievedHoliday;
import toy.test.holidaymanager.holiday.application.port.in.RemoveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RetrieveHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    private final RetrieveHolidaysUseCase retrieveHolidaysUseCase;
    private final RemoveHolidaysUseCase removeHolidaysUseCase;
    private final RenewHolidaysUseCase renewHolidaysUseCase;

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

    @PostMapping("/{year}/{countryCode}/refresh")
    public void renewHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode
    ) throws JsonProcessingException {
        renewHolidaysUseCase.execute(RenewCommand.from(year, countryCode));
    }

    @DeleteMapping("/{year}/{countryCode}")
    public void removeHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode
    ) {
        removeHolidaysUseCase.execute(RemoveCommand.from(year, countryCode));
    }
}
