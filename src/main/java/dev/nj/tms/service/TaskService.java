package dev.nj.tms.service;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.web.dto.NewTaskDto;

public interface TaskService {
    Task createTask(NewTaskDto dto, AppUser appUser);
}
