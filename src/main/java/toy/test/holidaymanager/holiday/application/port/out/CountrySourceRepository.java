package toy.test.holidaymanager.holiday.application.port.out;

import toy.test.holidaymanager.holiday.domain.vo.CountryCode;

import java.util.List;

public interface CountrySourceRepository {
    List<CountryCode> findAll();
}
