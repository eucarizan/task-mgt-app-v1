package dev.nj.tms.web;

import dev.nj.tms.config.WebSecurityConfig;
import dev.nj.tms.model.AppUser;
import dev.nj.tms.repositories.AppUserRepository;
import dev.nj.tms.service.impl.UserDetailsServiceImpl;
import dev.nj.tms.web.controller.TaskController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import({WebSecurityConfig.class, UserDetailsServiceImpl.class})
public class TaskControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private AppUserRepository userRepository;

    private final String TEST_EMAIL = "test@mail.com";
    private final String TEST_PASSWORD = "password123";

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
}
