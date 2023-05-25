package bani.lux.banikzn.controllers;

import bani.lux.banikzn.dto.BookingResponseDto;
import bani.lux.banikzn.dto.NewBookingDto;
import bani.lux.banikzn.dto.NewComplexDto;
import bani.lux.banikzn.dto.converters.BookingConverter;
import bani.lux.banikzn.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final BookingConverter bookingConverter;

    @PostMapping(value = "/add/booking", consumes = "application/json")
    public String addBooking(@RequestBody BookingResponseDto BookingResponseDto, ModelMap model) {
        NewBookingDto newBookingDto = bookingConverter.from(BookingResponseDto);
        if (!BookingResponseDto.getCost().equals(0)){
            bookingService.saveBooking(newBookingDto);
        }
        String id = String.valueOf(newBookingDto.getComplexId());
        return "redirect:/payment?id=" + id;
    }

    @GetMapping(value = "/payment")
    public String payment(@RequestParam("id") String id) {
        return "redirect:/complexes/" + id;
    }
}
