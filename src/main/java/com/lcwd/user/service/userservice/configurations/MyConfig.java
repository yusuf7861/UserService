package com.lcwd.user.service.userservice.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfig {

    @Bean
    // the @LoadBalanced annotation tells Spring Cloud to create a Ribbon-backed RestTemplate, so dynamically name of the service is used instead of hardcoding the, localhosts
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
