package bani.lux.banikzn.models;

import bani.lux.banikzn.utils.OpenCloseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "schedule", cascade = CascadeType.MERGE)
    private Complex complex;

    @ElementCollection
    @CollectionTable(name = "day_time_schedule")
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    @AttributeOverrides({
            @AttributeOverride(name = "openTime", column = @Column(name = "open_time")),
            @AttributeOverride(name = "closeTime", column = @Column(name = "close_time"))
    })
    private Map<DayOfWeek, OpenCloseTime> workSchedule;

    @CollectionTable(name = "day_price")
    @ElementCollection(targetClass=Integer.class)
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<DayOfWeek, Integer> hourCost;
}


