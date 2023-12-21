package br.com.conhecimento.integrationtests.controllers.cors.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.conhecimento.configs.v1.TestConfig;
import br.com.conhecimento.integrationtests.mocks.v1.KnowledgeMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v1.KnowledgeVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class KnowledgeControllerCorsTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static KnowledgeMock mock;
	private static KnowledgeVO knowledge;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mock = new KnowledgeMock();
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/knowledge")
				.setPort(TestConfig.SERVER_PORT)
				.setContentType(TestConfig.CONTENT_TYPE_JSON)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	void testCreateWithWrongOrigin() {
		knowledge = mock.vo();
		
		var content = given().spec(specification)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANOTHER_HOST)
				.body(knowledge)
				.when()
					.post()
				.then()
					.statusCode(403)
				.extract()
					.body()
					.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(2)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		knowledge = mock.vo();
		
		var content = given().spec(specification)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
				.body(knowledge)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		KnowledgeVO createdKnowledge = mapper.readValue(content, KnowledgeVO.class);
		knowledge = createdKnowledge;
		
		assertTrue(createdKnowledge.getKey() > 0);
		
		assertEquals("Title0", createdKnowledge.getTitle());
		assertEquals("Description0", createdKnowledge.getDescription());
		assertEquals("Content0", createdKnowledge.getContent());
		assertEquals("Esti", createdKnowledge.getSoftware().getName());
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v1/knowledge?page=0&size=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testDeleteByIdWithWrongOrigin() {
		
		var content = given().spec(specification)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANOTHER_HOST)
				.pathParam("id", knowledge.getKey())
				.when()
					.delete("{id}")
				.then()
					.statusCode(403)
				.extract()
					.body()
					.asString();
		
		assertNotNull(content);
		assertEquals("Invalid CORS request", content);
	}
	
	@Test
	@Order(4)
	void testDeleteById() {
		
		given().spec(specification)
			.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
			.pathParam("id", knowledge.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
}
