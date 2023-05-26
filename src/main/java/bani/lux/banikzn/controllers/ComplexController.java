package bani.lux.banikzn.controllers;

import bani.lux.banikzn.dto.*;
import bani.lux.banikzn.models.Complex;
import bani.lux.banikzn.models.User;
import bani.lux.banikzn.repositories.ComplexRepository;
import bani.lux.banikzn.repositories.UserRepository;
import bani.lux.banikzn.services.BookingService;
import bani.lux.banikzn.services.ComplexService;

import bani.lux.banikzn.utils.DayOfWeekConverter;
import bani.lux.banikzn.utils.OpenCloseTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ComplexController {
    private final ComplexRepository complexRepository;
    private final UserRepository userRepository;
    private final ComplexService complexService;
    private final BookingService bookingService;


    @GetMapping("/add/complex")
    public String showNewComplexForm(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        if (user.getRole().equals(User.Role.ADMIN)) {
            model.addAttribute("test", principal.getName());
            NewComplexDto newComplexDto = new NewComplexDto();
            model.addAttribute("newComplexDto", newComplexDto);
            return "index";
        }
        else {
            model.addAttribute("code", "403");
            model.addAttribute("errorMessageLocalized", "Forbidden");
            return "error";
        }


    }

    @GetMapping(value = "/complexes/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public String getComplexWithId(@PathVariable Long id, Model model) {
        ComplexDto complex = complexService.getComplexById(id);
        NewBookingDto newBookingDto = new NewBookingDto();
        Map<DayOfWeek, OpenCloseTime> workSchedule = complex.getSchedule().getWorkSchedule();
        String hiddenDaysString = DayOfWeekConverter.convertToIndices(workSchedule);
        model.addAttribute("schedule", workSchedule);
        model.addAttribute("newBookingDto", newBookingDto);
        model.addAttribute("hiddenDays", hiddenDaysString);
        model.addAttribute("titleText", complex.getName());
        model.addAttribute("id", id);
        model.addAttribute("complexEntity",complex);
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

    @GetMapping("/complexes")
    public String showComplexes(@RequestParam(required = false, defaultValue = "name_asc") String sort, Model model) {
        List<Complex> complexes = switch (sort) {
            case "name_asc" -> complexRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
            case "name_desc" -> complexRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
            case "price_asc" -> complexRepository.findAll(Sort.by(Sort.Direction.ASC, "placeQuantity"));
            case "price_desc" -> complexRepository.findAll(Sort.by(Sort.Direction.DESC, "placeQuantity"));
            default -> complexRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        };
        //TODO average cost
        model.addAttribute("complexes", complexes);
        return "complexes";
    }

}
