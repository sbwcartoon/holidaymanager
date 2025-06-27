package toy.test.holidaymanager.holiday.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.adapter.out.persistence.mapper.HolidayJpaMapper;
import toy.test.holidaymanager.holiday.adapter.out.persistence.repository.HolidayJpaRepository;
import toy.test.holidaymanager.holiday.application.port.in.command.RemoveCommand;
import toy.test.holidaymanager.holiday.application.port.in.command.RetrieveFilterCommand;
import toy.test.holidaymanager.holiday.application.port.in.vo.HolidayYear;
import toy.test.holidaymanager.holiday.application.port.out.HolidayRepository;
import toy.test.holidaymanager.holiday.domain.model.Holiday;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class HolidayAdapter implements HolidayRepository {
    private final HolidayJpaRepository holidayJpaRepository;

    @Override
    public void saveAll(final List<Holiday> holidays) {
        final List<HolidayJpaEntity> entities = holidays.stream().map(HolidayJpaMapper::toEntity).toList();
        holidayJpaRepository.saveAll(entities);
    }

    @Override
    public Page<Holiday> findAllByCondition(final RetrieveFilterCommand command, final Pageable pageable) {
        final Page<HolidayJpaEntity> result = holidayJpaRepository.findAllByCondition(
                command.countryCode().value(),
                command.startDate(),
                command.endDate(),
                command.types(),
                pageable
        );

        return result.map(HolidayJpaMapper::toDomain);
    }

    @Transactional
    @Override
    public void deleteAllByCondition(final RemoveCommand command) {
        final List<HolidayJpaEntity> entities = holidayJpaRepository.findAllByYearAndCountryCode(
                command.year().value(),
                command.countryCode().value()
        );
        if (entities.isEmpty()) {
            return;
        }
        holidayJpaRepository.deleteAll(entities);
    }

    @Transactional
    @Override
    public void deleteAllByYear(final HolidayYear year) {
        final List<HolidayJpaEntity> entities = holidayJpaRepository.findAllByYearAndCountryCode(year.value(), null);
        if (entities.isEmpty()) {
            return;
        }
        holidayJpaRepository.deleteAll(entities);
    }

    @Override
    public long count() {
        return holidayJpaRepository.count();
    }
}
