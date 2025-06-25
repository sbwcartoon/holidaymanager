package toy.test.holidaymanager.holiday.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

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
}
