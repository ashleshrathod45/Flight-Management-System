package com.searchservice.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI flightServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Flight Service API")
                    .description("API Documentation for Flight Service")
                    .version("1.0"));
    }
}
