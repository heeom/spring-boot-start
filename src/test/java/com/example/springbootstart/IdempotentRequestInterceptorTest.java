package com.example.springbootstart;

import com.example.springbootstart.exception.DuplicateRequestException;
import com.example.springbootstart.interceptor.IdempotentRequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class IdempotentRequestInterceptorTest {

    @Mock(strictness = Mock.Strictness.LENIENT)
    private RedissonClient redissonClient;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private IdempotentRequestInterceptor interceptor;

    private RBucket bucket;

    @BeforeEach
    void setUp() {
        bucket = mock(RBucket.class);
        when(redissonClient.getBucket(anyString())).thenReturn(bucket);
    }

    @Test
    void GET_중복_요청_검증_통과() throws Exception {
        // given
        when(request.getMethod()).thenReturn("GET");

        // when
        boolean result = interceptor.preHandle(request, response, new Object());

        Assertions.assertTrue(result);
        // Redis 호출 없음
        verifyNoInteractions(redissonClient);
    }

    @Test
    void POST_중복_요청_예외발생() throws Exception {
        when(request.getMethod()).thenReturn("POST");
        when(request.getHeader("Idempotency-Key")).thenReturn("duplicate-key");

        // 중복요청
        when(bucket.setIfAbsent(anyString(), any(Duration.class))).thenReturn(false);

        assertThrows(
                DuplicateRequestException.class,
                () -> interceptor.preHandle(request, response, new Object()),
                "POST 중복 요청시 DuplicateRequestException 발생"
        );
        verify(bucket).setIfAbsent(eq("duplicate-key"), any(Duration.class));
    }
}
