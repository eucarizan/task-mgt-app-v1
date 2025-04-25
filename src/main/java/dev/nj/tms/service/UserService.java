package dev.nj.tms.service;

import dev.nj.tms.web.dto.NewUserDto;

public interface UserService {
    void registerUser(NewUserDto newUserDto);
}
