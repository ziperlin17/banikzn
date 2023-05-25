package bani.lux.banikzn.services;

import bani.lux.banikzn.dto.NewUserDto;
import bani.lux.banikzn.models.User;

public interface UserService {
    void saveUser(NewUserDto newUserDto);
}
