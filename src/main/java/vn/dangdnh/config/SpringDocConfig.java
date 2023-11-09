package vn.dangdnh.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().components(
                new Components()).info(
                new Info().title("Identity Access Management")
                        .description("Identity Access Management API Document"));
    }
}
