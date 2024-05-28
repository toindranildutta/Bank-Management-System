package dev.indranil.idb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "ID Bank App",
				description = "Backend Rest Apis for ID Bank",
				version = "v1.0",
				contact = @Contact(
						name = "Indranil Dutta",
						email = "toindranildutta@gmail.com",
						url = "https://github.com/toindranildutta/Bank-Management-System"
						),
				license = @License(
						name = "ID Bank",
						url = "https://github.com/toindranildutta/Bank-Management-System"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "ID Bank App documentation",
				url = "https://github.com/toindranildutta/Bank-Management-System"
				)
		)
public class IdBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdBankApplication.class, args);
	}

}
