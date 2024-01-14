package br.com.migrations.integrationtests.controllers.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.migrations.configs.v1.TestConfigs;
import br.com.migrations.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.migrations.model.v1.FlywaySchemaHistory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class FlywaySchemaHistoryControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/flyway/migration")
				.setPort(TestConfigs.SERVER_PORT)
				.setContentType(TestConfigs.CONTENT_TYPE_JSON)
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var resultList = objectMapper.readValue(content, new TypeReference<List<FlywaySchemaHistory>>() {});
		
		FlywaySchemaHistory flywayZero = resultList.get(0);

		assertNotNull(flywayZero.getInstalleRank());
		assertNotNull(flywayZero.getVersion());
		assertNotNull(flywayZero.getDescription());
		assertNotNull(flywayZero.getType());
		assertNotNull(flywayZero.getScript());
		assertNotNull(flywayZero.getChecksum());
		assertNotNull(flywayZero.getInstalledBy());
		assertNotNull(flywayZero.getInstalledOn());
		assertNotNull(flywayZero.getExecutionTime());
		assertNotNull(flywayZero.getSuccess());
		
		FlywaySchemaHistory flywayTwelve = resultList.get(12);
		
		assertNotNull(flywayTwelve.getInstalleRank());
		assertNotNull(flywayTwelve.getVersion());
		assertNotNull(flywayTwelve.getDescription());
		assertNotNull(flywayTwelve.getType());
		assertNotNull(flywayTwelve.getScript());
		assertNotNull(flywayTwelve.getChecksum());
		assertNotNull(flywayTwelve.getInstalledBy());
		assertNotNull(flywayTwelve.getInstalledOn());
		assertNotNull(flywayTwelve.getExecutionTime());
		assertNotNull(flywayTwelve.getSuccess());
	}
	
}
