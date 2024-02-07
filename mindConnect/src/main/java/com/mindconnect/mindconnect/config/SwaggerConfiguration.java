package com.mindconnect.mindconnect.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("MindConnect API - Relieving  the mind")
                                .version("1.0")
                                .description("Your safe abode for casting your fears and comforting your mind")
                                .contact(
                                        new Contact()
                                                .name("SQ018 Java Devs")
                                                .url("https://github.com/decadevs/mind-connect-api")
                                )
                                .license(
                                        new License()
                                                .name("Apache 2.4")
                                                .url("https://github.com/decadevs/mind-connect-api")
                                )
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "Bearer Authentication",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("Bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .externalDocs(
                        new ExternalDocumentation()
                                .description("MindConnect REST API Documentation")
                                .url("https://github.com/decadevs/mind-connect-api")
                );
    }
}
