package toy.test.holidaymanager.holiday.adapter.out.persistence.mapper;

import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayTypeJpaEntity;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.*;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HolidayJpaMapper {

    public static HolidayJpaEntity toEntity(final Holiday holiday) {
        final HolidayJpaEntity entity = HolidayJpaEntity.builder()
                .id(holiday.getId().toString())
                .countryCode(holiday.getCountryCode().value())
                .date(holiday.getDate())
                .localName(holiday.getLocalName().value())
                .name(holiday.getName().value())
                .global(holiday.getGlobal().value())
                .launchYear(Objects.isNull(holiday.getLaunchYear()) ? null : holiday.getLaunchYear().value())
                .build();
        if (Objects.nonNull(holiday.getCounties())) {
            holiday.getCounties().forEach(it -> entity.addHolidayCounty(it.value()));
        }
        holiday.getTypes().forEach(entity::addHolidayTypeCode);

        return entity;
    }

    public static Holiday toDomain(final HolidayJpaEntity entity) {
        return Holiday.builder()
                .id(new HolidayId(UUID.fromString(entity.getId())))
                .countryCode(new CountryCode(entity.getCountryCode()))
                .date(entity.getDate())
                .localName(new HolidayLocalName(entity.getLocalName()))
                .name(new HolidayName(entity.getName()))
                .global(new Global(entity.isGlobal()))
                .launchYear(Objects.isNull(entity.getLaunchYear()) ? null : new LaunchYear(entity.getLaunchYear()))
                .counties(Objects.isNull(entity.getCounties()) ? null : entity.getCounties().stream()
                        .map(it -> new HolidayCounty(it.getCode())).collect(Collectors.toSet()))
                .types(entity.getTypes().stream().map(HolidayTypeJpaEntity::getCode).collect(Collectors.toSet()))
                .build();
    }
}
