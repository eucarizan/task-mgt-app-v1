package dev.nj.tms.service;

import dev.nj.tms.dictionaries.TaskStatus;
import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.repositories.AppUserRepository;
import dev.nj.tms.repositories.TaskRepository;
import dev.nj.tms.service.impl.TaskServiceImpl;
import dev.nj.tms.web.dto.NewTaskDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTests {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private static final String TEST_EMAIL = "test@mail.com";
    private final UserDetails testUser = User.withUsername(TEST_EMAIL)
            .password("testPass!")
            .build();
    private final LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0);

    @Test
    void createTask_ShouldSaveAndReturnTask() {
        NewTaskDto request = new NewTaskDto("Test title", "Test description");
        AppUser appUser = new AppUser(TEST_EMAIL, "encodedPassword");
        Task expectedTask = new Task(request.title(), request.description(), appUser);
        expectedTask.setId(1L);

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(appUser));
        when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);

        Task result = taskService.createTask(request, testUser);

        assertNotNull(result.getId());
        assertEquals(request.title(), result.getTitle());
        assertEquals(request.description(), result.getDescription());
        assertEquals(TaskStatus.CREATED, result.getStatus());
        assertEquals(appUser, result.getAuthor());
        assertNotNull(result.getCreatedAt());

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_NullTitle_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto(null, "Test description");
        AppUser appUser = new AppUser(TEST_EMAIL, "encodedPassword");

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(appUser));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testUser));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_EmptyTitle_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto("  ", "Test description");
        AppUser appUser = new AppUser(TEST_EMAIL, "encodedPassword");

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(appUser));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testUser));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_NullDescription_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto("Test title", null);
        AppUser appUser = new AppUser(TEST_EMAIL, "encodedPassword");

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(appUser));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testUser));

        verifyNoInteractions(taskRepository);
    }

    @Test
    void createTask_EmptyDescription_ThrowsExceptionTest() {
        NewTaskDto invalidRequest = new NewTaskDto("Test title", "   ");
        AppUser appUser = new AppUser(TEST_EMAIL, "encodedPassword");

        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(appUser));

        assertThrows(IllegalArgumentException.class,
                () -> taskService.createTask(invalidRequest, testUser));

        verifyNoInteractions(taskRepository);
    }

}
