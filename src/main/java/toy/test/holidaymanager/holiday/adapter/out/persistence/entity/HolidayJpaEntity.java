package toy.test.holidaymanager.holiday.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import toy.test.holidaymanager.holiday.domain.vo.HolidayId;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Entity
@Table(
        name = "holiday",
        indexes = {
                @Index(name = "idx_holiday_country_code_date", columnList = "country_code, date"),
                @Index(name = "idx_holiday_date_country_code_local_name", columnList = "date, country_code, local_name")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HolidayJpaEntity {
    @Builder.Default
    @EqualsAndHashCode.Include
    @Id
    @Column(length = 64)
    private String id = HolidayId.generate().toString();

    @Column(length = 2, nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String localName;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean global;

    @Column
    private Integer launchYear;

    @Builder.Default
    @OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HolidayCountyJpaEntity> counties = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "holiday", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HolidayTypeJpaEntity> types = new HashSet<>();

    public void addHolidayCounty(final String holidayCounty) {
        counties.add(HolidayCountyJpaEntity.builder()
                .holiday(this)
                .code(holidayCounty)
                .build());
    }

    public void addHolidayTypeCode(final HolidayTypeCode holidayTypeCode) {
        types.add(HolidayTypeJpaEntity.builder()
                .holiday(this)
                .code(holidayTypeCode)
                .build());
    }
}
