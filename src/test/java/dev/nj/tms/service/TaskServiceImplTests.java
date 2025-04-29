package dev.nj.tms.service;

import dev.nj.tms.dictionaries.TaskStatus;
import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.repositories.TaskRepository;
import dev.nj.tms.service.impl.TaskServiceImpl;
import dev.nj.tms.web.dto.NewTaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private final AppUser testAuthor = new AppUser("test@mail.com", "testPass!");
    private final LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0);

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        NewTaskDto request = new NewTaskDto("Test title", "Test description");

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L);
            savedTask.setCreatedAt(fixedTime);
            return savedTask;
        });

        Task result = taskService.createTask(request, testAuthor);

        assertNotNull(result.getId());
        assertEquals(request.title(), result.getTitle());
        assertEquals(request.description(), result.getDescription());
        assertEquals(TaskStatus.CREATED, result.getStatus());
        assertEquals(testAuthor, result.getAuthor());
        assertNotNull(result.getCreatedAt());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_NullTitle_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto(null, "Test description");

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testAuthor));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_EmptyTitle_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto("  ", "Test description");

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testAuthor));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_NullDescription_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto("Test title", null);

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testAuthor));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_EmptyDescription_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto("Test title", "   ");

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testAuthor));

        verifyNoInteractions(taskRepository);
    }

}
