package toy.test.holidaymanager.holiday.adapter.out.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;

public interface HolidayJpaRepository extends JpaRepository<HolidayJpaEntity, String> {
}
