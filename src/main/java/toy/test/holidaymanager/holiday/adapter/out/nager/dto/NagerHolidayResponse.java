package toy.test.holidaymanager.holiday.adapter.out.nager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
                .localName(new HolidayLocalName(localName))
                .name(new HolidayName(name))
                .countryCode(new CountryCode(countryCode))
                .global(new Global(global))
                .counties(Objects.isNull(counties) ? null : counties.stream().map(HolidayCounty::new).toList())
                .launchYear(new LaunchYear(launchYear))
                .types(types.stream().map(HolidayTypeCode::valueOf).toList())
                .build();
    }

    private LocalDate convertToLocalDate(final String date) {
        final String[] dateParts = date.split("-");
        final int year = Integer.parseInt(dateParts[0]);
        final int month = Integer.parseInt(dateParts[1]);
        final int day = Integer.parseInt(dateParts[2]);
        return LocalDate.of(year, month, day);
    }
}
