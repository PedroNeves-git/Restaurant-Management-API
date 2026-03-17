package br.com.Restaurant.Management.API.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        System.out.println("Swagger OpenAPI Configurado!");
        String securitySchemeName = "bearerAuth";

        String description = "API for restaurant management.<br><br>" +
                "<strong>Development Team:</strong><ul>" +
                "<li>Breno Rosa Barbosa (breno.barbosa422@gmail.com)</li>" +
                "<li>Guilherme dos Santos Lima (gslima0806@gmail.com)</li>" +
                "<li>Guilherme Januário Fonseca (guilhermejfonseca@hotmail.com)</li>" +
                "<li>Lidia Maciel Gonçalves (maciellili@hotmail.com)</li>" +
                "<li>Pedro Henrique Neves dos Santos (sgtpedrocos@gmail.com)</li>" +
                "</ul>";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title("Restaurant-Management-API")
                        .description(description)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Official Repository (GitHub)")
                                .url("https://github.com/PedroNeves-git/Restaurant-Management-API")
                        )
                )
                .tags(List.of(
                        new Tag().name("Users"),
                        new Tag().name("Users Types")
                ));
//                return new OpenAPI()
//                .components(new Components().addSecuritySchemes("basicScheme",
//                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))
//                .info(new Info()
//                        .title("Restaurant-Management-API")
//                        .description(description)
//                        .version("1.0.0")
//                        .contact(new Contact()
//                                .name("Official Repository (GitHub)")
//                                .url("https://github.com/PedroNeves-git/Restaurant-Management-API")
//                        )
//                )
//                        .tags(List.of(
//                                new Tag().name("Users"),
//                                new Tag().name("Users Types")
//                        ));
    }

}