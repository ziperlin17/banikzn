package bani.lux.banikzn.services.impl;

import bani.lux.banikzn.dto.NewUserDto;
import bani.lux.banikzn.exceptions.UserExistsException;
import bani.lux.banikzn.models.User;
import bani.lux.banikzn.repositories.UserRepository;
import bani.lux.banikzn.services.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Builder
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void saveUser(NewUserDto newUserDto) {
        if (!userRepository.existsUserByEmail(newUserDto.getEmail())){
            userRepository.save(User.builder()
                    .email(newUserDto.getEmail())
                    .firstName(newUserDto.getFirstName())
                    .hashPassword(passwordEncoder.encode(newUserDto.getHashPassword()))
                    .phone(newUserDto.getPhone())
                    .lastName(newUserDto.getLastName())
                    .register_date(LocalDateTime.now())
                    .state(User.State.CONFIRMED)
                    .role(User.Role.USER).build());
        }
        else {
            throw new UserExistsException("User already exists for the given email.");
        }
    }
}
