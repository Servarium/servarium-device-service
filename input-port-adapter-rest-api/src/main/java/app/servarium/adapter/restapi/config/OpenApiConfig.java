package app.servarium.adapter.restapi.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Device Service API",
                description = "API для взаимодействия с микросервисом устройств",
                version = "1.0.0",
                contact = @Contact(
                        name = "Egor Belykh",
                        email = "eg.belykh@yandex.ru",
                        url = "https://t.me/popipopich"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        description = "Аутентификация через JWT токены",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@Configuration
public class OpenApiConfig {
}
