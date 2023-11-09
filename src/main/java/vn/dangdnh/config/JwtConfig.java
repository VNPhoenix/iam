package vn.dangdnh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class JwtConfig {

    // Default expiration time is 15 minutes
    @Value("${jwt.expirationTime:PT15M}")
    private Duration duration;

    public long getExpirationInSecond() {
        return duration.getSeconds();
    }

    public long getExpirationInMillis() {
        return duration.toMillis();
    }
}
