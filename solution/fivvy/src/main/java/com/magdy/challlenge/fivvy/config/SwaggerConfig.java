package com.magdy.challlenge.fivvy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Fivvy Challenge Spring Boot API")
                        .version("1.0.0")
                        .description(" Disclaimer App Spring Boot with Swagger")
                        .termsOfService("http://swagger.io/terms/")
                        );
    }
}