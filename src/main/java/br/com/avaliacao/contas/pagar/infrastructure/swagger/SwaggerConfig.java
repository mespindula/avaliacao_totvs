package br.com.avaliacao.contas.pagar.infrastructure.swagger;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
/*
    @Value("${swagger.path}")
    private String swaggerPath;

    @Bean
    public Docket allApi() {

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        parameterBuilder.name(SecurityConstants.HEADER_STRING).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(parameterBuilder.build());

        Set<String> protocols = new HashSet<String>();
        protocols.add("http");
        protocols.add("https");

        return new Docket(DocumentationType.SWAGGER_2)
                .host(swaggerPath)
                .groupName("All")
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .build()
                .protocols(protocols)
                .ignoredParameterTypes(ApiIgnore.class)
                .enableUrlTemplating(true)
                .globalOperationParameters(parameters);
    }*/

    /*private ApiInfo apiInfo() {

        @SuppressWarnings("rawtypes")
        ApiInfo apiInfo = new ApiInfo(
                "Contas a pagar API REST",
                "API REST para Sistema de Contas a Pagar.",
                "1.0",
                "Terms of Service",
                new Contact("Maicon Espindula", "",
                        "mespindula@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
        );

        return apiInfo;
    }*/

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
                                .url("http://www.seusite.com.br")
                        )
                ).externalDocs(
                        new ExternalDocumentation()
                                .description("Maicon Espindula")
                                .url("http://www.seusite.com.br"));
    }
}
