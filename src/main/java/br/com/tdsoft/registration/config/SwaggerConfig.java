package br.com.tdsoft.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;


@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
        .info(new Info().title("Banco de dados de cliente").description("É um projeto em backend de Gerenciador de Clientes, desenvolvido utilizando Java (Spring Boot) e PostgreSQL. A API RESTful permite operações de criação, leitura, atualização e exclusão (CRUD) de clientes.").version("1"))
        .schemaRequirement("authetication", creaSecurityScheme());
    }

    private SecurityScheme creaSecurityScheme(){

        return new SecurityScheme()
        .name("authetication")
        .type(SecurityScheme.Type.HTTP)
        .scheme("basic")
        .in(SecurityScheme.In.HEADER);
    }


}
