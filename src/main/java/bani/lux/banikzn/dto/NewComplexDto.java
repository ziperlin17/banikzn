package bani.lux.banikzn.dto;

import bani.lux.banikzn.models.Schedule;
import bani.lux.banikzn.utils.OpenCloseTime;
import lombok.*;
import org.yaml.snakeyaml.emitter.ScalarAnalysis;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewComplexDto {
    private String name;
    private String description;
    private String address;
    private Integer placeQuantity;
    private Map<DayOfWeek, OpenCloseTime> workSchedule;
    private Map<DayOfWeek, Integer> hourCost;
}
