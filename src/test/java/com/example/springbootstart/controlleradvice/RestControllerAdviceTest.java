package com.example.springbootstart.controlleradvice;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
    @DisplayName("SpecialApi 에서 발생한 예외는 SpecialExceptionHandler 에서 핸들링 되어야 한다.")
    void specialApi_shouldHandledBySpecialExceptionHandler() throws Exception {
        mockMvc.perform(get("/api/special"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Handled By illegalArgumentExceptionHandler")));
    }

    @Test
    @DisplayName("NormalApi 에서 발생한 예외는 SpecialExceptionHandler 에서 핸들링 되지 않는다.")
    void normalApi_doesNotHandledBySpecialExceptionHandler() {
        Exception exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/api/normal")).andReturn();
        });

        Throwable cause = exception.getCause();
        assertInstanceOf(IllegalArgumentException.class, cause);
        assertEquals("ExceptionInvoker throw ===> IllegalArgumentException", cause.getMessage());
    }
}
