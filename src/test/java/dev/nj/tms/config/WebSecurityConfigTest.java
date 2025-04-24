package dev.nj.tms.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(WebSecurityConfig.class)
public class WebSecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenUnauthenticated_thenApiTaskEndpointIsProtected() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAuthenticated_thenApiTaskEndpointIsAccessible() throws Exception {
        mockMvc.perform(get("/api/tasks")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk());
    }

}
