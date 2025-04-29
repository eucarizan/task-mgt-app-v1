package dev.nj.tms.web.controller;

import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.service.TaskService;
import dev.nj.tms.web.dto.NewTaskDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<Void> getTasks() {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid NewTaskDto dto,
                                           @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(taskService.createTask(dto, user));
    }
}
