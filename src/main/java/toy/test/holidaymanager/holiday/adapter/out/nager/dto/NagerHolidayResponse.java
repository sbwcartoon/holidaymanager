package toy.test.holidaymanager.holiday.adapter.out.nager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NagerHolidayResponse {
    private String date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean global;
    private List<String> counties;
    private Integer launchYear;
    private List<String> types;

    public Holiday toDomain() {
        return Holiday.builder()
                .date(convertToLocalDate(date))
                .localName(localName)
                .name(name)
                .countryCode(countryCode)
                .global(global)
                .counties(counties)
                .launchYear(launchYear)
                .types(types.stream().map(HolidayTypeCode::valueOf).toList())
                .build();
    }

    private LocalDate convertToLocalDate(String date) {
        final String[] dateParts = date.split("-");
        final int year = Integer.parseInt(dateParts[0]);
        final int month = Integer.parseInt(dateParts[1]);
        final int day = Integer.parseInt(dateParts[2]);
        return LocalDate.of(year, month, day);
    }
}
