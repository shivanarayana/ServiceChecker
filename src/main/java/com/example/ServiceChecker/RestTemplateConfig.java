package com.example.ServiceChecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    //It creates and registers a RestTemplate bean in the Spring application context.
    //This bean is required as we injected into the HealthCheckExecutorService

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}