package bani.lux.banikzn.controllers;

import bani.lux.banikzn.dto.NewUserDto;
import bani.lux.banikzn.exceptions.UserExistsException;
import bani.lux.banikzn.repositories.UserRepository;
import bani.lux.banikzn.services.UserService;
import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
                              @RequestParam("password") String password) {
        return new ModelAndView("redirect:/dashboard");
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("newUserDto", new NewUserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("newUserDto") NewUserDto newUserDto, Model model) {
        if (userRepository.existsUserByEmail(newUserDto.getEmail())) {
            model.addAttribute("errorMessage", "User already exists for the given email.");
            return "registration";
        }

        userService.saveUser(newUserDto);
        model.addAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/add/complex";
    }
}
