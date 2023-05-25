package bani.lux.banikzn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDto {
    private Long complexId;
    private Integer cost;
    private String startTime;
    private String endTime;
    private String dateStart;
    private String dateEnd;
}