package com.attornatus.project.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("People API")
                        .version("v1")
                        .description("API de controle de pessoas")
                        .contact(new Contact().name("Marcos Bilobram").url("https://www.linkedin.com/in/marcosbilobram/"))
                );
    }

}