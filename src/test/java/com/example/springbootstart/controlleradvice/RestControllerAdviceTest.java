package com.example.springbootstart.controlleradvice;

import com.example.springbootstart.interceptor.IdempotentRequestInterceptor;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
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
        mockMvc.perform(get("/api/special"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Handled By illegalArgumentExceptionHandler")));
    }

    @Test
    void normalController에는_advice적용안된다() throws Exception {
        Exception exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/normal")).andReturn();
        });

        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalArgumentException.class, cause);
        assertEquals("ExceptionInvoker throw ===> IllegalArgumentException", cause.getMessage());
    }
}
