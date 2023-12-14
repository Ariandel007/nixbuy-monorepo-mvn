package com.mvnnixbuyapi.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Collections;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
//        SpringApplication.run(EurekaServerApplication.class, args);
        SpringApplication app = new SpringApplication(EurekaServerApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "3800"));
        app.run(args);
    }

}
