package br.com.coffeeandit.payments.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@OpenAPIDefinition(
        info =@Info(
                title = "API para para pagamentos na Stripe",
                version = "${api.version}",
                contact = @Contact(
                        name = "CoffeeAndIt", email = "coffeeandit@coffeeandit.com.br", url = "https://coffeeandit.com.br/"
                ),
                license = @License(
                        name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "${tos.uri}",
                description = "API para para pagamentos na Stripe"
        ),
        servers = @Server(
                url = "http://localhost:8080",
                description = "Production"
        ), security = { @SecurityRequirement(name = "Bearer Authentication")}
)
public class OpenAPISecurityConfiguration {
}
