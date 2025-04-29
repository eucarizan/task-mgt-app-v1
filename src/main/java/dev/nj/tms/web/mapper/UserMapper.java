package dev.nj.tms.web.mapper;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.web.dto.NewUserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public AppUser toEntity(NewUserDto dto) {
        return new AppUser(
                dto.email().toLowerCase(),
                encoder.encode(dto.password())
        );
    }
}
