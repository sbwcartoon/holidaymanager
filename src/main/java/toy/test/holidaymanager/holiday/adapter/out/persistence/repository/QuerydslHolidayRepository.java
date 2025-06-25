package toy.test.holidaymanager.holiday.adapter.out.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.List;

public interface QuerydslHolidayRepository {
    Page<HolidayJpaEntity> findAllByCondition(
            final String countryCode,
            final LocalDate startDate,
            final LocalDate endDate,
            final List<HolidayTypeCode> types,
            final Pageable pageable
    );
}
