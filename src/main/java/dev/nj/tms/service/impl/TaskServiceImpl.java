package dev.nj.tms.service.impl;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.repositories.TaskRepository;
import dev.nj.tms.service.TaskService;
import dev.nj.tms.web.dto.NewTaskDto;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(NewTaskDto dto, AppUser appUser) {
        Task task = new Task(dto.title(), dto.description(), appUser);
        return taskRepository.save(task);
    }
}
