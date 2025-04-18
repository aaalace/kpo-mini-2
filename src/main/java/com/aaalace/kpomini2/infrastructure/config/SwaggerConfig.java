package com.aaalace.kpomini2.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI zooManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API зоопарка")
                        .version("1.0"));
    }
} 