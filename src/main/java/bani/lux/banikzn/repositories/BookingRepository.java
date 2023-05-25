package bani.lux.banikzn.repositories;

import bani.lux.banikzn.models.Booking;
import bani.lux.banikzn.models.Complex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findAllByComplexId(Long complexId);
    List<Booking> findAllByComplexIdAndDateStart(Long complex_id, LocalDate date);
}
