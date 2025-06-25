package toy.test.holidaymanager.holiday.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import toy.test.holidaymanager.holiday.domain.vo.HolidayTypeCode;

import java.util.UUID;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Entity
@Table(name = "holiday_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HolidayTypeJpaEntity {
    @Builder.Default
    @EqualsAndHashCode.Include
    @Id
    @Column(length = 64)
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_id")
    private HolidayJpaEntity holiday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HolidayTypeCode code;
}
