package toy.test.holidaymanager.holiday.adapter.out.nager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NagerCountryResponse {
    private String countryCode;
    private String name;

    public CountryCode toDomain() {
        return new CountryCode(countryCode);
    }
}
