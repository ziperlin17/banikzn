package bani.lux.banikzn.dto;

import bani.lux.banikzn.models.User;

import java.time.LocalDateTime;

public class UserDto {
    private String email;
    private String hashPassword;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime register_date;
    private User.State state;
    private User.Role role;
}
