package com.personal.post.config;

import com.personal.post.client.MemberApiClient;
import com.personal.post.client.MemberApiClientImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberApiClient memberApiClient() {
        return new MemberApiClientImpl();
    }
}