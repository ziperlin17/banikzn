package bani.lux.banikzn.dto.converters;

import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.models.Complex;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class ComplexConverter {
    public ComplexDto from(Complex complex){
        return ComplexDto.builder()
                .id(complex.getId())
                .name(complex.getName())
                .placeQuantity(complex.getPlaceQuantity())
                .description(complex.getDescription())
                .schedule(complex.getSchedule())
                .address(complex.getAddress()).build();
    }
    public List<ComplexDto> from(List<Complex> complexes) {
        return complexes.stream().map(this::from).toList();
    }
}
