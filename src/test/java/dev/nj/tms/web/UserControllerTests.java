package dev.nj.tms.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nj.tms.TaskManagementSystemApplication;
import dev.nj.tms.config.WebSecurityConfig;
import dev.nj.tms.exceptions.UserAlreadyExistsException;
import dev.nj.tms.service.UserService;
import dev.nj.tms.web.controller.UserController;
import dev.nj.tms.web.dto.NewUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {
        TaskManagementSystemApplication.class,
        WebSecurityConfig.class
})
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void register_ValidRequest_Returns200() throws Exception {
        NewUserDto dto = new NewUserDto("test@mail.com", "Password123!");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk());

        verify(userService).registerUser(dto);
    }

    @Test
    public void register_BlankEmail_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto("", "Password123!");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_NullEmail_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto(null, "Password123!");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_BadEmail_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto(" ", "Password123!");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_MalformedEmail_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto("address@mail.", "Password123!");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_BlankPassword_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto("test@mail.com", "");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_NullPassword_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto("test@mail.com", null);
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_BadPassword_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto("test@mail.com", " ");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_LessThanMinLengthPassword_Returns400() throws Exception {
        NewUserDto dto = new NewUserDto("test@mail.com", "pass");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isBadRequest());

        verify(userService, never()).registerUser(any());
    }

    @Test
    public void register_NotUniqueEmail_Returns409() throws Exception {
        doThrow(new UserAlreadyExistsException())
                .when(userService).registerUser(any(NewUserDto.class));

        NewUserDto dto = new NewUserDto("test@mail.com", "Password123!");
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isConflict());

        verify(userService).registerUser(any());
    }

    private static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
