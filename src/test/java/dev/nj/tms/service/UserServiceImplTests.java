package dev.nj.tms.service;

import dev.nj.tms.exceptions.UserAlreadyExistsException;
import dev.nj.tms.model.AppUser;
import dev.nj.tms.repositories.AppUserRepository;
import dev.nj.tms.service.impl.UserServiceImpl;
import dev.nj.tms.web.dto.NewUserDto;
import dev.nj.tms.web.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser_NewEmail_SavesUser() {
        NewUserDto dto = new NewUserDto("test@mail.com", "Password123!");
        AppUser mockUser = new AppUser(dto.email(), dto.password());

        when(userMapper.toEntity(dto)).thenReturn(mockUser);
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);

        userService.registerUser(dto);

        verify(userRepository).save(mockUser);
    }

    @Test
    void registerUser_DuplicateEmail_ThrowsException() {
        NewUserDto dto = new NewUserDto("duplicate@mail.com", "Password123!");
        AppUser mockUser = new AppUser(dto.email(), dto.password());

        when(userMapper.toEntity(dto)).thenReturn(mockUser);
        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () ->
                userService.registerUser(dto));

        verify(userRepository, never()).save(any());
    }

}
