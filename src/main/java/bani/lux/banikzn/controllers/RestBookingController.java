package bani.lux.banikzn.controllers;

import bani.lux.banikzn.dto.BookingRequestDto;
import bani.lux.banikzn.repositories.BookingRepository;
import bani.lux.banikzn.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class RestBookingController {
    private final BookingService bookingService;

    @PostMapping(value = "/calendar-data/{id}/checkBooking", consumes = "application/json")
    public Map<Boolean,Integer> checkBooking(@PathVariable Long id, @RequestBody BookingRequestDto bookingRequest) {
        Map<Boolean,Integer> isBookingValid = bookingService.checkNewBooking(bookingRequest);
        return isBookingValid;

    }
}
