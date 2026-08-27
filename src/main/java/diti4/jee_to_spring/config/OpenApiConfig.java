package diti4.jee_to_spring.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Gestion des Produits",
                version = "1.0",
                description = "API REST permettant de gérer les produits et leurs types.",
                contact = @Contact(
                        name = "DITI4"
                )
        )
)
public class OpenApiConfig {
}