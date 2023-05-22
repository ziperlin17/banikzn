package bani.lux.banikzn.controllers;

import bani.lux.banikzn.dto.BookingDto;
import bani.lux.banikzn.dto.ComplexDto;
import bani.lux.banikzn.dto.NewBookingDto;
import bani.lux.banikzn.dto.NewComplexDto;
import bani.lux.banikzn.models.Booking;
import bani.lux.banikzn.models.Complex;
import bani.lux.banikzn.services.BookingService;
import bani.lux.banikzn.services.ComplexService;

import bani.lux.banikzn.utils.DayOfWeekConverter;
import bani.lux.banikzn.utils.OpenCloseTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ComplexController {
    private final ComplexService complexService;
    private final BookingService bookingService;


    @GetMapping("/add/complex")
    public String showNewComplexForm(Model model) {
        NewComplexDto newComplexDto = new NewComplexDto();
        model.addAttribute("newComplexDto", newComplexDto);
        log.error(newComplexDto.getName());
        return "index";
    }

    @GetMapping(value = "/complexes/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getComplexWithId(@PathVariable Long id, Model model) {
        ComplexDto complex = complexService.getComplexById(id);
        Map<DayOfWeek, OpenCloseTime> workSchedule = complex.getSchedule().getWorkSchedule();
        String hiddenDaysString = DayOfWeekConverter.convertToIndices(workSchedule);
        model.addAttribute("schedule", workSchedule);
        model.addAttribute("hiddenDays", hiddenDaysString);
        model.addAttribute("titleText", complex.getName());
        return "/calendar";
    }


    @PostMapping(value = "/add/complex")
    public String createComplex(@ModelAttribute("newComplexDto") NewComplexDto newComplexDto) {
        complexService.saveComplex(newComplexDto);
        String id = String.valueOf(complexService.getComplexIdByName(newComplexDto.getName()));
        return "redirect:/complexes/"+id;
    }

    @PostMapping("/complex/{id}")
    public String createBooking(@PathVariable Long id,
                                @ModelAttribute ("newBookingDto")NewBookingDto newBookingDto,
                                Principal principal) {

        return "redirect:/complex/{id}";
    }

    @GetMapping("/calendar-data/{id}")
    @ResponseBody
    public List<BookingDto> getCalendarData(@PathVariable("id") Long complexId) {
        return bookingService.getBookingData(complexId);
    }

    @GetMapping("/calendar-data/{id}/checkBooking")
    @ResponseBody
    public Boolean checkNewBooking(@PathVariable("id") Long complexId,
                                         @ModelAttribute("newComplexDto") NewBookingDto newBookingDto) {
        return bookingService.checkNewBooking(newBookingDto);
    }


}
