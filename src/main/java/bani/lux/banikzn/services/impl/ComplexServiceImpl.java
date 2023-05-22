package bani.lux.banikzn.services.impl;

import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.dto.NewComplexDto;
import bani.lux.banikzn.dto.converters.ComplexConverter;
import bani.lux.banikzn.models.Complex;
import bani.lux.banikzn.models.Schedule;
import bani.lux.banikzn.repositories.ComplexRepository;
import bani.lux.banikzn.services.ComplexService;
import bani.lux.banikzn.utils.OpenCloseTime;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class ComplexServiceImpl implements ComplexService {
    private final ComplexRepository complexRepository;
    private final ComplexConverter complexConverter;

    @Override
    public void saveComplex(NewComplexDto newComplexDto) {
        Map<DayOfWeek, OpenCloseTime> workSchedule = newComplexDto.getWorkSchedule().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new OpenCloseTime(entry.getValue().getOpenTime(), entry.getValue().getCloseTime())
                ));

        Complex savedComplex = complexRepository.save(Complex.builder()
                        .name(newComplexDto.getName())
                        .description(newComplexDto.getDescription())
                        .address(newComplexDto.getAddress())
                        .placeQuantity(newComplexDto.getPlaceQuantity())
                        .schedule(Schedule.builder()
                                .workSchedule(workSchedule)
                                .hourCost(newComplexDto.getHourCost())
                                .build())
                .build());
    }

    @Override
    public List<ComplexDto> getAllComplexes() {
        return complexConverter.from(complexRepository.findAll());
    }

    @Override
    public ComplexDto getComplexById(Long id) {
        Complex complex = complexRepository.findComplexById(id).orElseThrow(() ->
                new EntityNotFoundException("Complex with id " + id + " not found"));
        return complexConverter.from(complex);
    }

    @Override
    public void deleteComplexById(Long id) {
        complexRepository.deleteById(id);
    }

    @Override
    public Long getComplexIdByName(String name) {
        return complexRepository.findComplexByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Complex with name "+ name +" not found"))
                .getId();
    }

    @Override
    public String getComplexNameById(Long id) {
        return complexRepository.findComplexById(id)
                .orElseThrow(() -> new EntityNotFoundException("Complex with id "+ id +" not found"))
                .getName();
    }
}
