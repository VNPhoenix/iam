package com.vht.client.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

@Configuration
@PropertySource("file:config/application.properties")
public class JwtConfig {

    // Default expiration time is 15 minutes
    @Getter
    @Value("${jwt.expirationTime:PT1H}")
    private Duration expirationDuration;
}
