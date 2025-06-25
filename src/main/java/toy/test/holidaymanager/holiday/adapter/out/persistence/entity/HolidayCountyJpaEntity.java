package toy.test.holidaymanager.holiday.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Entity
@Table(name = "holiday_county")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HolidayCountyJpaEntity {
    @Builder.Default
    @EqualsAndHashCode.Include
    @Id
    @Column(length = 64)
    private String id = UUID.randomUUID().toString();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "holiday_id")
    private HolidayJpaEntity holiday;

    @Column(nullable = false)
    private String code;
}
