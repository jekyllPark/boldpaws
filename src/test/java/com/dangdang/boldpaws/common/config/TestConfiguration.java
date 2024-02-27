package com.dangdang.boldpaws.common.config;

import com.dangdang.boldpaws.common.security.component.TokenProvider;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfiguration {
    @MockBean
    private TokenProvider tokenProvider;
}
