package dev.nj.tms.web.mapper;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.web.dto.NewUserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public AppUser toEntity(NewUserDto dto) {
        return new AppUser(
                dto.email(),
                dto.password()
        );
    }
}
