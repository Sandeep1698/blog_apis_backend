package com.deere.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        Contact contact = new Contact();
        contact.setName("John Deere");
        contact.setEmail("JohnDeereAdmin@deere.com");
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        Components components = new Components().addSecuritySchemes("jwt", securityScheme);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("jwt", Collections.emptyList());


        return new OpenAPI()
                .info(new Info().title("Blog Apis")
                        .description("Implementation of blog APIs")
                        .version("v1").contact(contact))
                .components(components)
                .security(Collections.singletonList(securityRequirement));
    }
}
