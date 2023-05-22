package bani.lux.banikzn.services;

import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.dto.NewComplexDto;
import bani.lux.banikzn.models.Complex;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComplexService {
    List<ComplexDto> getAllComplexes();
    ComplexDto getComplexById(Long id);
    void saveComplex(NewComplexDto newComplexDto);
    void deleteComplexById(Long id);
    Long getComplexIdByName(String name);
    String getComplexNameById(Long id);
}
