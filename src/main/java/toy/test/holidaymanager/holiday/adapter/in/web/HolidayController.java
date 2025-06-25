package toy.test.holidaymanager.holiday.adapter.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import toy.test.holidaymanager.holiday.adapter.in.dto.PageResponse;
import toy.test.holidaymanager.holiday.adapter.in.dto.RetrievedHoliday;
import toy.test.holidaymanager.holiday.application.port.in.RemoveCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RenewCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.RetrieveCountryHolidaysUseCase;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RenewCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    private final RetrieveCountryHolidaysUseCase retrieveCountryHolidaysUseCase;
    private final RemoveCountryHolidaysUseCase removeCountryHolidaysUseCase;
    private final RenewCountryHolidaysUseCase renewCountryHolidaysUseCase;

    @GetMapping("/{year}/{countryCode}")
    public PageResponse<RetrievedHoliday> getHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode,
            @RequestParam(required = false) final Integer from,
            @RequestParam(required = false) final Integer to,
            @RequestParam(required = false) final List<String> types,
            @PageableDefault(size = 10) final Pageable pageable
    ) {
        Page<Holiday> holidays = retrieveCountryHolidaysUseCase.execute(
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
        renewCountryHolidaysUseCase.execute(RenewCommand.from(year, countryCode));
    }

    @DeleteMapping("/{year}/{countryCode}")
    public void removeHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode
    ) {
        removeCountryHolidaysUseCase.execute(RemoveCommand.from(year, countryCode));
    }
}
