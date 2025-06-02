package com.mvnnixbuyapi.configuration;

import feign.Client;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClient {
    @Bean
    public Client feignClient() {
        return new OkHttpClient();
    }}
