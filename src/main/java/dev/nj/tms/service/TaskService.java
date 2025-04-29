package dev.nj.tms.service;

import dev.nj.tms.model.Task;
import dev.nj.tms.web.dto.NewTaskDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface TaskService {
    Task createTask(NewTaskDto dto, UserDetails appUser);
}
