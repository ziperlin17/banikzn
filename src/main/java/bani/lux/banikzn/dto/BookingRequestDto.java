package bani.lux.banikzn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDto {
    private Long complexId;
    private String startTime;
    private String endTime;
    private String dateStart;
    private String dateEnd;
}