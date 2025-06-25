package toy.test.holidaymanager.holiday.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;
import toy.test.holidaymanager.holiday.domain.vo.CountryCode;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class HolidayAdapter implements HolidayRepository {
    private final HolidayJpaRepository holidayJpaRepository;

    @Override
    public void saveAll(final List<Holiday> holidays) {
        List<HolidayJpaEntity> entities = holidays.stream().map(HolidayJpaMapper::toEntity).toList();
        holidayJpaRepository.saveAll(entities);
    }

    @Override
    public Page<Holiday> findAllByCondition(
            final CountryCode countryCode,
            final LocalDate startDate,
            final LocalDate endDate,
            final List<HolidayTypeCode> types,
            final Pageable pageable
    ) {
        Page<HolidayJpaEntity> result = holidayJpaRepository.findAllByCondition(
                countryCode.value(),
                startDate,
                endDate,
                types,
                pageable
        );

        return result.map(HolidayJpaMapper::toDomain);
    }
}
