package toy.test.holidaymanager.holiday.adapter.out.persistence.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import toy.test.holidaymanager.holiday.adapter.out.persistence.entity.HolidayJpaEntity;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static toy.test.holidaymanager.holiday.adapter.out.persistence.entity.QHolidayCountyJpaEntity.holidayCountyJpaEntity;
import static toy.test.holidaymanager.holiday.adapter.out.persistence.entity.QHolidayJpaEntity.holidayJpaEntity;
import static toy.test.holidaymanager.holiday.adapter.out.persistence.entity.QHolidayTypeJpaEntity.holidayTypeJpaEntity;

@RequiredArgsConstructor
public class QuerydslHolidayRepositoryImpl implements QuerydslHolidayRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<HolidayJpaEntity> findAllByCondition(
            final String countryCode,
            final LocalDate startDate,
            final LocalDate endDate,
            final List<HolidayTypeCode> types,
            final Pageable pageable
    ) {
        List<BooleanExpression> predicates = new ArrayList<>();
        predicates.add(holidayJpaEntity.countryCode.eq(countryCode));
        predicates.add(holidayJpaEntity.date.between(startDate, endDate));
        if (Objects.nonNull(types) && !types.isEmpty()) {
            predicates.add(holidayTypeJpaEntity.code.in(types));
        }

        List<HolidayJpaEntity> result = queryFactory
                .selectFrom(holidayJpaEntity)
                .distinct()
                .leftJoin(holidayJpaEntity.types, holidayTypeJpaEntity).fetchJoin()
                .leftJoin(holidayJpaEntity.counties, holidayCountyJpaEntity).fetchJoin()
                .where(predicates.toArray(new BooleanExpression[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(holidayJpaEntity.date.asc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(holidayJpaEntity.countDistinct())
                .from(holidayJpaEntity)
                .leftJoin(holidayJpaEntity.types, holidayTypeJpaEntity)
                .leftJoin(holidayJpaEntity.counties, holidayCountyJpaEntity)
                .where(predicates.toArray(new BooleanExpression[0]));

        return PageableExecutionUtils.getPage(
                result,
                pageable,
                () -> Objects.requireNonNullElse(countQuery.fetchOne(), 0L)
        );
    }

    @Override
    public List<HolidayJpaEntity> findAllByYearAndCountryCode(final int year, final String countryCode) {
        return queryFactory
                .selectFrom(holidayJpaEntity)
                .distinct()
                .leftJoin(holidayJpaEntity.types, holidayTypeJpaEntity).fetchJoin()
                .leftJoin(holidayJpaEntity.counties, holidayCountyJpaEntity).fetchJoin()
                .where(
                        holidayJpaEntity.date.year().eq(year),
                        holidayJpaEntity.countryCode.eq(countryCode)
                )
                .orderBy(holidayJpaEntity.date.asc())
                .fetch();
    }
}
