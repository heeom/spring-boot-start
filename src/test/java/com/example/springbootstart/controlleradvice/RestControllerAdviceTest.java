package com.example.springbootstart.controlleradvice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void specialController에만_advice적용된다() throws Exception {
        mockMvc.perform(get("/special"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Handled By illegalArgumentExceptionHandler")));
    }

    @Test
    void normalController에는_advice적용안된다() throws Exception {
        mockMvc.perform(get("/normal")
                        .param("error", "true"))
                .andExpect(status().isInternalServerError()); // 500 에러 뜨고, advice 처리 안 됨
    }
}
