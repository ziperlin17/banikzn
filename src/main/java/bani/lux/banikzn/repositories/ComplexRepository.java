package bani.lux.banikzn.repositories;

import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.models.Complex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ComplexRepository extends JpaRepository<Complex,Long> {
    Optional<Complex> findComplexById(Long id);
    Optional<Complex> findComplexByName(String name);
}
