package com.example.popcorndatabase.system;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SystemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnInfo() throws Exception {
        this.mockMvc
            .perform(get("/info"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.branch").isNotEmpty())
            .andExpect(jsonPath("$.commit_id").isNotEmpty())
            .andExpect(jsonPath("$.commit_message").isNotEmpty());
    }
}
