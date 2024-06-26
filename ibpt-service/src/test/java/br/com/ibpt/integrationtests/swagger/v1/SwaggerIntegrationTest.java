package br.com.ibpt.integrationtests.swagger.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import br.com.ibpt.configs.v1.TestConfigs;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class SwaggerIntegrationTest {

	@Test
	public void shouldDisplaySwaggerUiPage() {
		var content = 
				given()
					.basePath("/swagger-ui/index.html")
					.port(TestConfigs.SERVER_PORT)
					.when()
						.get()
					.then()
						.statusCode(200)
					.extract()
						.body().asString();
		
		assertTrue(content.contains("Swagger UI"));
	}
}
