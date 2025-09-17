package com.example.ufo_fi.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "local server url"),
                @Server(url = "https://api.ufo-fi.store", description = "deploy server url")
        }
)
@Configuration
public class SwaggerConfig {
    private static final String JWT_TOKEN_COOKIE = "jwt 토큰 넣어주십셔~!";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(createComponents())
                .addSecurityItem(new SecurityRequirement().addList(JWT_TOKEN_COOKIE))
                .info(createApiInfo());
    }

    private io.swagger.v3.oas.models.info.Info createApiInfo() {
        return new io.swagger.v3.oas.models.info.Info()
                .title("UFO-FI API")
                .description("UFO-FI API 명세서")
                .version("1.0.0");
    }

    private Components createComponents() {
        return new Components()
                .addSecuritySchemes(JWT_TOKEN_COOKIE, createAccessTokenSecurityScheme());
    }

    private SecurityScheme createAccessTokenSecurityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.COOKIE)
                .name("Authorization");
    }
}
