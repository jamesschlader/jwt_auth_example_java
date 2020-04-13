package com.schlader.james.jwtauthexample;

import com.schlader.james.jwtauthexample.config.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class JwtAuthExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthExampleApplication.class, args);
    }

}
