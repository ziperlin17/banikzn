package bani.lux.banikzn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserDto {

    private String email;
    private String hashPassword;
    private String firstName;
    private String lastName;
    private String phone;

}

