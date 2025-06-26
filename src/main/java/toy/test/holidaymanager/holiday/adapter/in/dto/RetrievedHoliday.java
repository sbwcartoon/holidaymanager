package toy.test.holidaymanager.holiday.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.HolidayCounty;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Schema(description = "공휴일 정보")
public record RetrievedHoliday(
        @Schema(description = "국가 코드(ISO 3166-1 alpha-2)", example = "KR")
        String countryCode,

        @Schema(description = "공휴일 날짜", example = "2025-10-06")
        String date,

        @Schema(description = "공휴일 이름(해당 국가 언어 표기)", example = "추석")
        String localName,

        @Schema(description = "공휴일 이름(영문 표기)", example = "Chuseok")
        String name,

        @Schema(description = "국가 전체 휴일 여부",
                type = "boolean",
                example = "true")
        boolean global,

        @Schema(description = "최초 적용 연도",
                type = "integer",
                example = "null")
        Integer launchYear,

        @Schema(description = "적용되는 state code list(ISO 3166-2 list. 빈 배열일 경우 국가 전체에 적용됨)",
                type = "array",
                example = "[\"KR-11\", \"KR-12\", \"KR-13\"]")
        List<String> counties,

        @Schema(description = "공휴일 유형(string list)",
                type = "array",
                allowableValues = {"Public", "Bank", "School", "Authorities", "Optional", "Observance"},
                example = "[\"Public\", \"School\"]")
        List<String> types
) {
    public static RetrievedHoliday from(final Holiday holiday) {
        return new RetrievedHoliday(
                holiday.getCountryCode().value(),
                holiday.getDate().format(DateTimeFormatter.ISO_DATE),
                holiday.getLocalName().value(),
                holiday.getName().value(),
                holiday.getGlobal().value(),
                Objects.isNull(holiday.getLaunchYear()) ? null : holiday.getLaunchYear().value(),
                Objects.isNull(holiday.getCounties()) ? null : holiday.getCounties().stream()
                        .map(HolidayCounty::value).toList(),
                holiday.getTypes().stream().map(Enum::name).toList()
        );
    }
}
