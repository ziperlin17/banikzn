package bani.lux.banikzn.dto;

import bani.lux.banikzn.models.Complex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewBookingDto {
    private Complex complex;

    private Integer cost;

    private LocalDate date;

    private LocalTime timeStart;

    private LocalTime timeEnd;
}
