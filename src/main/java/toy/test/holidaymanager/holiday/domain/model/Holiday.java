package toy.test.holidaymanager.holiday.domain.model;

import lombok.Builder;
import lombok.Getter;
import toy.test.holidaymanager.holiday.domain.vo.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@Getter
public class Holiday {
    @Builder.Default
    private HolidayId id = HolidayId.generate();
    private CountryCode countryCode;
    private LocalDate date;
    private HolidayLocalName localName;
    private HolidayName name;
    private Global global;
    private LaunchYear launchYear;
    private Set<HolidayCounty> counties;
    private Set<HolidayTypeCode> types;
}
