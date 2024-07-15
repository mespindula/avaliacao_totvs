package br.com.avaliacao.contas.pagar.infrastructure.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Contas a pagar API REST")
                        .description("API REST para Sistema de Contas a Pagar.")
                        .version("1.0")
                        .termsOfService("Terms of Service")
                        .license(new License()
                                .name("Apache 2.0")
                        )
                ).externalDocs(
                        new ExternalDocumentation()
                                .description("Maicon Espindula"));
    }
}
