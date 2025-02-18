package br.com.tdsoft.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Banco de dados de cliente", description ="É um projeto em backend de Gerenciador de Clientes, desenvolvido utilizando Java (Spring Boot) e PostgreSQL. A API RESTful permite operações de criação, leitura, atualização e exclusão (CRUD) de clientes.", version = "1.0"))
public class RegistrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrationApplication.class, args);
	}

}
