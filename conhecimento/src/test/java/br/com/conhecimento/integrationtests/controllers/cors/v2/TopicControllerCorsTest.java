package br.com.conhecimento.integrationtests.controllers.cors.v2;

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
import br.com.conhecimento.integrationtests.mocks.v1.TopicMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v1.TopicVO;
import br.com.conhecimento.integrationtests.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.integrationtests.vo.v2.TokenVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class TopicControllerCorsTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static TopicMock mock;
	private static TopicVO topic;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mock = new TopicMock();
	}
	
	@Test
	@Order(0)
	void testAuthorization() {
		
		AccountCredentialsVO user = new AccountCredentialsVO("henrique", "he@01");
		
		var tokenVO = given()
				.basePath("/auth/signin")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()	
					.body()
					.as(TokenVO.class);
				
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/topic")
				.setPort(TestConfig.SERVER_PORT)
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.setContentType(TestConfig.CONTENT_TYPE_JSON)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	void testCreateWithWrongOrigin() {
		topic = mock.vo();
		
		var content = given().spec(specification)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANOTHER_HOST)
				.body(topic)
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
		topic = mock.vo();
		
		var content = given().spec(specification)
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
				.body(topic)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TopicVO createdTopic = mapper.readValue(content, TopicVO.class);
		topic = createdTopic;
		
		assertTrue(createdTopic.getKey() > 0);
		
		assertEquals("Name0", createdTopic.getName());
		assertTrue(content.contains("\"topicVOList\":{\"href\":\"http://localhost:8888/v1/topic?page=0&size=10&sortBy=name&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testDeleteByIdWithWrongOrigin() {

		var content = given().spec(specification)
				.pathParam("id", topic.getKey())
				.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_ANOTHER_HOST)
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
			.pathParam("id", topic.getKey())
			.header(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_LOCALHOST)
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
		
	}
	
}
