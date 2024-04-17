package br.com.conhecimento.configs.v1;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(
					new Info()
						.title("RESTful API for Jave's Knowledge Application with Java 21 and Spring Boot 3")
						.version("v1")
						.description("API with Registration of Knowledges and Topics")
						.termsOfService("http://javeinformatica.com.br/terms")
						.license(
								new License()
									.name("Apache 2.0")
									.url("http://javeinformatica.com.br/terms")
						)
				);
	}
	
}
