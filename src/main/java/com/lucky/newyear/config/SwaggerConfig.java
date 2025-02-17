package com.lucky.newyear.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Swip API",  // 어떤 API 명세서를 위한 Swagger 페이지인지
                description = "API documentaion for Swip project", // 설명
                version = "v1"
        ))
@Configuration
public class SwaggerConfig {
    @Value("${spring.server.api.url}")
    private String apiUrl;
    @Bean
    public OpenAPI api() {    //Authorization 처리

        Server httpsServer = new Server();
        httpsServer.setDescription("HTTPS");
        httpsServer.setUrl(apiUrl);

        Server localServer = new Server();
        localServer.setDescription("Local");
        localServer.setUrl("http://localhost:8080");

        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token");

        return new OpenAPI()
                .servers(List.of(httpsServer, localServer))
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement);
    }
}
