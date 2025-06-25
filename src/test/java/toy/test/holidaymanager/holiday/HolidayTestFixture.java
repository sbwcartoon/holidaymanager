package toy.test.holidaymanager.holiday;

import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HolidayTestFixture {

    public static List<HolidayJpaEntity> generateHolidayJpaEntities() {
        final List<HolidayJpaEntity> data = new ArrayList<>();

        final HolidayJpaEntity e = HolidayJpaEntity.builder()
                .countryCode("US")
                .date(LocalDate.of(2025, 1, 1))
                .localName("New Year's Day")
                .name("New Year's Day")
                .global(true)
                .launchYear(null)
                .build();
        e.addHolidayTypeCode(HolidayTypeCode.Public);
        data.add(e);

        final HolidayJpaEntity e0 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 1, 30))
                .localName("설날")
                .name("Lunar New Year")
                .global(false)
                .launchYear(null)
                .build();
        e0.addHolidayTypeCode(HolidayTypeCode.Public);
        e0.addHolidayCounty("KR-11");
        data.add(e0);

        final HolidayJpaEntity e1 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 3, 1))
                .localName("3·1절")
                .name("Independence Movement Day")
                .global(false)
                .launchYear(null)
                .build();
        e1.addHolidayTypeCode(HolidayTypeCode.Public);
        e1.addHolidayTypeCode(HolidayTypeCode.School);
        e1.addHolidayCounty("KR-11");
        e1.addHolidayCounty("KR-12");
        data.add(e1);

        final HolidayJpaEntity e2 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 5, 5))
                .localName("어린이날")
                .name("Children's Day")
                .global(true)
                .launchYear(null)
                .build();
        e2.addHolidayTypeCode(HolidayTypeCode.Public);
        e2.addHolidayTypeCode(HolidayTypeCode.School);
        data.add(e2);

        final HolidayJpaEntity e3 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2025, 5, 5))
                .localName("부처님 오신 날")
                .name("Buddha's Birthday")
                .global(true)
                .launchYear(null)
                .build();
        e3.addHolidayTypeCode(HolidayTypeCode.Public);
        e3.addHolidayTypeCode(HolidayTypeCode.Observance);
        data.add(e3);

        final HolidayJpaEntity e4 = HolidayJpaEntity.builder()
                .countryCode("KR")
                .date(LocalDate.of(2026, 5, 5))
                .localName("어린이날")
                .name("Children's Day")
                .global(true)
                .launchYear(null)
                .build();
        e4.addHolidayTypeCode(HolidayTypeCode.Public);
        e4.addHolidayTypeCode(HolidayTypeCode.School);
        data.add(e4);

        return data;
    }
}
