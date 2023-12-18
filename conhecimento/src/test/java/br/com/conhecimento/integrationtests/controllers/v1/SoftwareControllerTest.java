package br.com.conhecimento.integrationtests.controllers.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.conhecimento.configs.v1.TestConfig;
import br.com.conhecimento.integrationtests.mocks.v1.SoftwareMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v1.SoftwareVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class SoftwareControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static SoftwareMock mock;
	private static SoftwareVO software;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mock = new SoftwareMock();
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/software")
				.setPort(TestConfig.SERVER_PORT)
				.setContentType(TestConfig.CONTENT_TYPE_JSON)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		software = mock.vo();
		
		var content = given().spec(specification)
				.body(software)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SoftwareVO createdSoftware = mapper.readValue(content, SoftwareVO.class);
		software = createdSoftware;
		
		assertTrue(createdSoftware.getKey() > 0);
		
		assertEquals("Name0", createdSoftware.getName());
		assertTrue(content.contains("\"VOList\":{\"href\":\"http://localhost:8888/v1/software\"}"));
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", software.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SoftwareVO persistedSoftware = mapper.readValue(content, SoftwareVO.class);
		
		assertEquals(software.getKey(), persistedSoftware.getKey());
		assertEquals("Name0", persistedSoftware.getName());
		assertTrue(content.contains("\"VOList\":{\"href\":\"http://localhost:8888/v1/software\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		software.setName(software.getKey() + "Name");
		
		var content = given().spec(specification)
				.pathParam("id", software.getKey())
				.body(software)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SoftwareVO persistedSoftware = mapper.readValue(content, SoftwareVO.class);
		
		assertEquals(software.getKey(), persistedSoftware.getKey());
		assertEquals(software.getKey() + "Name", persistedSoftware.getName());
		assertTrue(content.contains("\"VOList\":{\"href\":\"http://localhost:8888/v1/software\"}"));
	}
	
	@Test
	@Order(4)
	void testDeleteById() {
		
		given().spec(specification)
			.pathParam("id", software.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var resultList = mapper.readValue(content, new TypeReference<List<SoftwareVO>>() {});
		
		SoftwareVO softwareOne = resultList.get(0);
		
		assertEquals(1, softwareOne.getKey());
		assertEquals("Esti", softwareOne.getName());
		
		SoftwareVO softwareTwo = resultList.get(1);
		
		assertEquals(2, softwareTwo.getKey());
		assertEquals("Stac", softwareTwo.getName());
	}
	
	@Test
	@Order(6)
	void testHATEOAS() {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		assertTrue(content.contains("{\"rel\":\"self\",\"href\":\"http://localhost:8888/v1/software/1\"}"));
		assertTrue(content.contains("{\"rel\":\"self\",\"href\":\"http://localhost:8888/v1/software/2\"}"));
	}
	
}
