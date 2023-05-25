package bani.lux.banikzn.controllers;

import bani.lux.banikzn.dto.NewUserDto;
import bani.lux.banikzn.exceptions.UserExistsException;
import bani.lux.banikzn.models.Token;
import bani.lux.banikzn.models.User;
import bani.lux.banikzn.repositories.TokenRepository;
import bani.lux.banikzn.repositories.UserRepository;
import bani.lux.banikzn.services.UserService;
import bani.lux.banikzn.utils.ElasticEmailClient;
import com.fasterxml.jackson.core.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private static final String apiKey = "E04D9D4790762F59F826C1200303D123C4CB806F6A7865BF5E5964631E57D22111211392D1F301E8DB37D8BC583811D1";
    private static final String url = "http://localhost:8080/token/verify/";
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
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
    public String registerUser(@ModelAttribute("newUserDto") NewUserDto newUserDto, Model model, RedirectAttributes redirectAttributes) {
        if (userRepository.existsUserByEmail(newUserDto.getEmail())) {
            model.addAttribute("errorMessage", "User already exists for the given email.");
            return "registration";
        }
        else {
            userService.saveUser(newUserDto);
            String newTokenString = UUID.randomUUID().toString();
            tokenRepository.save(Token.builder()
                    .user(userRepository.findByEmail(newUserDto.getEmail()).orElseThrow())
                    .token(newTokenString)
                    .build());
            ElasticEmailClient elasticEmailClient = new ElasticEmailClient();
            elasticEmailClient.send("ziperlin",apiKey,"danilsolovevinf@gmail.com","ziperlin",newTokenString,url+newTokenString+"/"+newUserDto.getEmail(),newUserDto.getEmail(),"true");
            model.addAttribute("successMessage", "Registration successful! Please log in.");
            redirectAttributes.addFlashAttribute("successMessage", "Verify your account by email");
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/token/verify/{acceptedToken}/{email}", method= {RequestMethod.GET, RequestMethod.POST})
    public String verifyToken(@PathVariable String acceptedToken, @PathVariable String email, RedirectAttributes redirectAttributes){
        Optional<Token> token = tokenRepository.findByToken(acceptedToken);
        if (token.isPresent()) {
            User userEntity = userRepository.findByEmail(email).orElseThrow();
            userEntity.setState(User.State.CONFIRMED);
            userRepository.save(userEntity);
            redirectAttributes.addFlashAttribute("successMessage", "Account Verified!");
            return "login";
        }
        else {
            return "403";
        }
    }

    @GetMapping("/403")
    public String getForbiden(){
        return "403";
    }
}
