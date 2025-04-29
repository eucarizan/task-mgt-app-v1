package dev.nj.tms.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nj.tms.config.WebSecurityConfig;
import dev.nj.tms.model.AppUser;
import dev.nj.tms.model.Task;
import dev.nj.tms.repositories.AppUserRepository;
import dev.nj.tms.service.TaskService;
import dev.nj.tms.service.impl.UserDetailsServiceImpl;
import dev.nj.tms.web.controller.TaskController;
import dev.nj.tms.web.dto.NewTaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({
        WebSecurityConfig.class,
        UserDetailsServiceImpl.class,
        TaskService.class})
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private TaskService taskService;
    @MockitoBean
    private AppUserRepository userRepository;

    private final String TEST_EMAIL = "test@mail.com";
    private final String TEST_PASSWORD = "password123";
    private final AppUser testUser = new AppUser(TEST_EMAIL, TEST_PASSWORD);

    @Test
    @WithMockUser
    void getTasks_WithAuthentication_Returns200() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }

    @Test
    void getTasks_WithValidBasicAuth_Returns200() throws Exception {
        AppUser testUser = new AppUser(TEST_EMAIL, passwordEncoder.encode(TEST_PASSWORD));
        when(userRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));

        mockMvc.perform(get("/api/tasks")
                        .with(httpBasic(TEST_EMAIL, TEST_PASSWORD)))
                .andExpect(status().isOk());

        verify(userRepository).findByEmail(any(String.class));
    }

    @Test
    void getTasks_WithInvalidBasicAuth_Returns401() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .with(httpBasic(TEST_EMAIL, TEST_PASSWORD)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getTasks_WithoutCredentials_Returns401() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void createTask_WithValidRequest_ReturnsCreatedTask() throws Exception {
        NewTaskDto request = new NewTaskDto("Test task", "Test description");
        Task response = new Task(request.title(), request.description(), testUser);

        when(taskService.createTask(any(NewTaskDto.class), any(UserDetails.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test task"))
                .andExpect(jsonPath("$.description").value("Test description"));
    }

    @Test
    @WithMockUser
    void createTask_WithBlankTitle_Returns400() throws Exception {
        NewTaskDto invalidRequest = new NewTaskDto(" ", "Test description");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("title should not be blank"));
    }

    @Test
    @WithMockUser
    void createTask_WithNullTitle_Returns400() throws Exception {
        NewTaskDto invalidRequest = new NewTaskDto(null, "Test description");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("title should not be blank"));
    }

    @Test
    @WithMockUser
    void createTask_WithNullDescription_Returns400() throws Exception {
        NewTaskDto invalidRequest = new NewTaskDto("Test title", null);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("description should not be blank"));
    }

    @Test
    @WithMockUser
    void createTask_WithBlankDescription_Returns400() throws Exception {
        NewTaskDto invalidRequest = new NewTaskDto("Test title", "  ");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("description should not be blank"));
    }

    @Test
    void createTask_Unauthenticated_Returns401() throws Exception {
        NewTaskDto request = new NewTaskDto("Test title", "Test description");

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isUnauthorized());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
