package dev.nj.tms.service.impl;

import dev.nj.tms.exceptions.UserAlreadyExistsException;
import dev.nj.tms.model.AppUser;
import dev.nj.tms.repositories.AppUserRepository;
import dev.nj.tms.service.UserService;
import dev.nj.tms.web.dto.NewUserDto;
import dev.nj.tms.web.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final AppUserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(
            AppUserRepository userRepository,
            UserMapper userMapper
    ){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void registerUser(NewUserDto newUserDto) {
        AppUser appUser = userMapper.toEntity(newUserDto);
        if (userRepository.existsByEmail(appUser.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        userRepository.save(appUser);
    }
}
