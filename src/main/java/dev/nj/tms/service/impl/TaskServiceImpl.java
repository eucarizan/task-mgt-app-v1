package dev.nj.tms.service.impl;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.repositories.AppUserRepository;
import dev.nj.tms.repositories.TaskRepository;
import dev.nj.tms.service.TaskService;
import dev.nj.tms.web.dto.NewTaskDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final AppUserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, AppUserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(NewTaskDto dto, UserDetails userDetails) {
        AppUser appUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Task task = new Task(dto.title(), dto.description(), appUser);
        return taskRepository.save(task);
    }
}
