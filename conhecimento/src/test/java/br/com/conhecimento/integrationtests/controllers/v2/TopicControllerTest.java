package br.com.conhecimento.integrationtests.controllers.v2;

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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.conhecimento.configs.v1.TestConfig;
import br.com.conhecimento.integrationtests.mocks.v1.TopicMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v1.TopicVO;
import br.com.conhecimento.integrationtests.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.integrationtests.vo.v2.TokenVO;
import br.com.conhecimento.integrationtests.vo.wrappers.v1.TopicWrapperVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class TopicControllerTest extends AbstractIntegrationTest {

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
	void testCreate() throws JsonMappingException, JsonProcessingException {
		topic = mock.vo();
		
		var content = given().spec(specification)
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
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", topic.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TopicVO persistedTopic = mapper.readValue(content, TopicVO.class);
		
		assertEquals(topic.getKey(), persistedTopic.getKey());
		assertEquals("Name0", persistedTopic.getName());
		assertTrue(content.contains("\"topicVOList\":{\"href\":\"http://localhost:8888/v1/topic?page=0&size=10&sortBy=name&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		topic.setName(topic.getKey() + "Name");
		
		var content = given().spec(specification)
				.pathParam("id", topic.getKey())
				.body(topic)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TopicVO updatedTopic = mapper.readValue(content, TopicVO.class);
		topic = updatedTopic;
		
		assertEquals(topic.getKey(), updatedTopic.getKey());
		assertEquals(topic.getKey() + "Name", updatedTopic.getName());
		assertTrue(content.contains("\"topicVOList\":{\"href\":\"http://localhost:8888/v1/topic?page=0&size=10&sortBy=name&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testDeleteById() {

		given().spec(specification)
			.pathParam("id", topic.getKey())
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
		
		var wrapper = mapper.readValue(content, TopicWrapperVO.class);
		
		List<TopicVO> resultList = wrapper.getEmbedded().getTopics();
		
		TopicVO topicOne = resultList.get(0);
		
		assertEquals(15, topicOne.getKey());
		assertEquals("Aviso", topicOne.getName());
		
		TopicVO topicTwo = resultList.get(1);
		
		assertEquals(23, topicTwo.getKey());
		assertEquals("Bat", topicTwo.getName());
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
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/topic/15\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/topic/23\"}"));
		
		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/v1/topic?page=0&size=10&sort=name,asc\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/topic?page=0&size=10&sort=name,asc\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/v1/topic?page=1&size=10&sort=name,asc\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/v1/topic?page=2&size=10&sort=name,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":24,\"totalPages\":3,\"number\":0}"));
	}
	
}
