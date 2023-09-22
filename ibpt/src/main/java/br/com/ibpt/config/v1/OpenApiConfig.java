package br.com.ibpt.config.v1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenApi() {
		return new OpenAPI()
				.info(
					new Info()
						.title("RESTful API for Jave's IBPT Update Application with Java 19 and Spring Boot 3")
						.version("v1")
						.description("API with Registration of Companies, Versions and Updates")
						.termsOfService("http://javeinformatica.com.br/terms")
						.license(
								new License()
									.name("Apache 2.0")
									.url("http://javeinformatica.com.br/terms")
						)
				);
	}
}
