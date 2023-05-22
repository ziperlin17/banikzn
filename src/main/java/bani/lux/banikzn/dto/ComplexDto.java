package bani.lux.banikzn.dto;

import bani.lux.banikzn.models.Complex;
import bani.lux.banikzn.models.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class ComplexDto {

    private Long id;
    private String name;
    private String description;
    private String address;
    private Integer placeQuantity;
    private Schedule schedule;

    public static ComplexDto from(Complex complex) {
        return ComplexDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .description(complex.getDescription())
                .address(complex.getAddress())
                .placeQuantity(complex.getPlaceQuantity())
                .schedule(complex.getSchedule()).build();
    }

    public static List<ComplexDto> from(List<Complex> complexList) {
        return complexList.stream()
                .map(complex -> ComplexDto.builder()
                        .id(complex.getId())
                        .name(complex.getName())
                        .description(complex.getDescription())
                        .address(complex.getAddress())
                        .placeQuantity(complex.getPlaceQuantity())
                        .build())
                .collect(Collectors.toList());
    }

}

