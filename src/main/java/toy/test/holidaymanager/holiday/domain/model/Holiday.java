package toy.test.holidaymanager.holiday.domain.model;

import lombok.Builder;
import lombok.Getter;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
public class Holiday {
    private LocalDate date;
    private String localName;
    private String name;
    private String countryCode;
    private boolean global;
    private List<String> counties;
    private Integer launchYear;
    private List<HolidayTypeCode> types;
}
