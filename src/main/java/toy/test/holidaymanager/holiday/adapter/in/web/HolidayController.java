package toy.test.holidaymanager.holiday.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "공휴일 관리")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/holidays")
public class HolidayController {
    private final RetrieveCountryHolidaysUseCase retrieveCountryHolidaysUseCase;
    private final RemoveCountryHolidaysUseCase removeCountryHolidaysUseCase;
    private final RenewCountryHolidaysUseCase renewCountryHolidaysUseCase;

    @Operation(
            summary = "공휴일 조회",
            description = "지정한 연도와 국가 코드에 해당하는 공휴일 목록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content()),
            },
            parameters = {
                    @Parameter(name = "year", description = "연도", example = "2025", required = true),
                    @Parameter(name = "countryCode", description = "국가 코드(ISO 3166-1 alpha-2)", example = "KR", required = true),
                    @Parameter(name = "from", description = "조회 시작 월(포함)", example = "1"),
                    @Parameter(name = "to", description = "조회 종료 월(포함)", example = "10"),
                    @Parameter(name = "types", description = "공휴일 유형",
                            schema = @Schema(type = "array",
                                    allowableValues = {"Public", "Bank", "School", "Authorities", "Optional", "Observance"}),
                            example = "[\"Public\", \"School\"]"),
                    @Parameter(name = "page", description = "페이지 번호(1부터 시작)",
                            schema = @Schema(type = "integer", defaultValue = "1"), example = "1"),
                    @Parameter(name = "size", description = "페이지별 항목 수",
                            schema = @Schema(type = "integer", defaultValue = "10"), example = "10"),
            }
    )
    @GetMapping("/{year}/{countryCode}")
    public PageResponse<RetrievedHoliday> getHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode,
            @RequestParam(required = false) final Integer from,
            @RequestParam(required = false) final Integer to,
            @RequestParam(required = false) final List<String> types,
            @Parameter(hidden = true) @PageableDefault(size = 10) final Pageable pageable
    ) {
        final Page<Holiday> holidays = retrieveCountryHolidaysUseCase.execute(
                RetrieveFilterCommand.from(year, countryCode, from, to, types),
                pageable
        );

        final Page<RetrievedHoliday> retrievedHolidays = holidays.map(RetrievedHoliday::from);
        return PageResponse.from(retrievedHolidays);
    }

    @Operation(
            summary = "공휴일 재동기화",
            description = "지정한 연도와 국가 코드에 해당하는 공휴일 목록을 최신화합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content()),
            },
            parameters = {
                    @Parameter(name = "year", description = "연도", example = "2025", required = true),
                    @Parameter(name = "countryCode", description = "국가 코드(ISO 3166-1 alpha-2)", example = "KR", required = true),
            }
    )
    @PostMapping("/{year}/{countryCode}/refresh")
    public void renewHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode
    ) {
        renewCountryHolidaysUseCase.execute(RenewCommand.from(year, countryCode));
    }

    @Operation(
            summary = "공휴일 삭제",
            description = "지정한 연도와 국가 코드에 해당하는 공휴일 목록을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content()),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content()),
            },
            parameters = {
                    @Parameter(name = "year", description = "연도", example = "2025", required = true),
                    @Parameter(name = "countryCode", description = "국가 코드(ISO 3166-1 alpha-2)", example = "KR", required = true),
            }
    )
    @DeleteMapping("/{year}/{countryCode}")
    public void removeHolidays(
            @PathVariable final int year,
            @PathVariable final String countryCode
    ) {
        removeCountryHolidaysUseCase.execute(RemoveCommand.from(year, countryCode));
    }
}
