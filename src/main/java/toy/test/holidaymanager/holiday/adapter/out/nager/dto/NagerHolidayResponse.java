package toy.test.holidaymanager.holiday.adapter.out.nager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NagerHolidayResponse {
    @JsonProperty(required = true)
    private String date;

    @JsonProperty(required = true)
    private String localName;

    @JsonProperty(required = true)
    private String name;

    @JsonProperty(required = true)
    private String countryCode;

    @JsonProperty(required = true)
    private boolean global;

    @JsonProperty(required = true)
    private List<String> counties;

    @JsonProperty(required = true)
    private Integer launchYear;

    @JsonProperty(required = true)
    private List<String> types;

    public Holiday toDomain() {
        return Holiday.builder()
                .countryCode(new CountryCode(countryCode))
                .date(convertToLocalDate(date))
                .localName(new HolidayLocalName(localName))
                .name(new HolidayName(name))
                .global(new Global(global))
                .launchYear(Objects.isNull(launchYear) ? null : new LaunchYear(launchYear))
                .counties(Objects.isNull(counties) ? Collections.emptySet() : counties.stream().map(HolidayCounty::new).collect(Collectors.toSet()))
                .types(types.stream().map(HolidayTypeCode::valueOf).collect(Collectors.toSet()))
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
