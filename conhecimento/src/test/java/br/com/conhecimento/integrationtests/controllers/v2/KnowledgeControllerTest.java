package br.com.conhecimento.integrationtests.controllers.v2;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import br.com.conhecimento.integrationtests.mocks.v2.KnowledgeMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.integrationtests.vo.v2.KnowledgeVO;
import br.com.conhecimento.integrationtests.vo.v2.TokenVO;
import br.com.conhecimento.integrationtests.vo.wrappers.v2.KnowledgeWrapperVO;
import br.com.conhecimento.model.v2.SoftwareKnwl;
import br.com.conhecimento.model.v2.TopicKnwl;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class KnowledgeControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static KnowledgeMock mock;
	private static KnowledgeVO knowledge;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mock = new KnowledgeMock();
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
				.setBasePath("/v2/knowledge")
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
		knowledge = mock.vo();
		
		var content = given().spec(specification)
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
		
		assertEquals("henrique", createdKnowledge.getUserCreation().getUsername());
		assertEquals(
			DateFormat.getDateInstance().format(new Date()), 
			DateFormat.getDateInstance().format(createdKnowledge.getLastUpdateDatetime())
		);
		assertEquals("henrique", createdKnowledge.getUserCreation().getUsername());
		assertEquals(
			DateFormat.getDateInstance().format(new Date()), 
			DateFormat.getDateInstance().format(createdKnowledge.getCreationDatetime())
		);
		
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v2/knowledge?page=0&size=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", knowledge.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		KnowledgeVO persistedKnowledge = mapper.readValue(content, KnowledgeVO.class);
		
		assertEquals(knowledge.getKey(), persistedKnowledge.getKey());
		assertEquals("Title0", persistedKnowledge.getTitle());
		assertEquals("Description0", persistedKnowledge.getDescription());
		assertEquals("Content0", persistedKnowledge.getContent());
		
		assertEquals("Esti", persistedKnowledge.getSoftware().getName());
		
		assertEquals("henrique", persistedKnowledge.getUserCreation().getUsername());
		assertEquals(
			DateFormat.getDateInstance().format(new Date()), 
			DateFormat.getDateInstance().format(persistedKnowledge.getLastUpdateDatetime())
		);
		assertEquals("henrique", persistedKnowledge.getUserCreation().getUsername());
		assertEquals(
			DateFormat.getDateInstance().format(new Date()), 
			DateFormat.getDateInstance().format(persistedKnowledge.getCreationDatetime())
		);
		
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v2/knowledge?page=0&size=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		
		knowledge.setTitle(knowledge.getKey() + "Title");
		knowledge.setDescription(knowledge.getKey() + "Description");
		knowledge.setContent(knowledge.getKey() + "Content");
		
		SoftwareKnwl software = new SoftwareKnwl(2);
		knowledge.setSoftware(software);
		
		List<TopicKnwl> topicList = new ArrayList<>();
		TopicKnwl topic = new TopicKnwl(1);
		TopicKnwl topic2 = new TopicKnwl(2);
		
		topicList.add(topic);
		topicList.add(topic2);
		
		knowledge.setTopics(topicList);
		
		var content = given().spec(specification)
				.pathParam("id", knowledge.getKey())
				.body(knowledge)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		KnowledgeVO updatedKnowledge = mapper.readValue(content, KnowledgeVO.class);
		knowledge = updatedKnowledge;
		
		assertEquals(knowledge.getKey(), updatedKnowledge.getKey());
		assertEquals(knowledge.getKey() + "Title", updatedKnowledge.getTitle());
		assertEquals(knowledge.getKey() + "Description", updatedKnowledge.getDescription());
		assertEquals(knowledge.getKey() + "Content", updatedKnowledge.getContent());
		assertEquals("Stac", updatedKnowledge.getSoftware().getName());
		
		assertEquals("henrique", updatedKnowledge.getUserCreation().getUsername());
		assertEquals("henrique", updatedKnowledge.getUserCreation().getUsername());
		assertTrue(
			updatedKnowledge.getLastUpdateDatetime()
			.after(updatedKnowledge.getCreationDatetime())
		);
		
		assertEquals("TG Config", updatedKnowledge.getTopics().get(0).getName());
		assertEquals("Instalação", updatedKnowledge.getTopics().get(1).getName());
		
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v2/knowledge?page=0&size=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testDeleteById() {
		
		given().spec(specification)
			.pathParam("id", knowledge.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	void testFindPageable() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var wrapper = mapper.readValue(content, KnowledgeWrapperVO.class);
		
		List<KnowledgeVO> resultList = wrapper.getEmbedded().getKnowledges();
	
		KnowledgeVO knowledgeOne = resultList.get(1);
		
		assertEquals(1, knowledgeOne.getKey());
		assertEquals("TG Config", knowledgeOne.getTitle());
		assertEquals("TG Config", knowledgeOne.getDescription());
		assertTrue(knowledgeOne.getContent().contains("<TGAPLICACAO name=\"Arca\" Caption=\"ARCA - Automação de Comércio e Indústria\">"));
		
		assertEquals("henrique", knowledgeOne.getUserCreation().getUsername());
		assertEquals(
			DateFormat.getDateInstance().format(new Date()), 
			DateFormat.getDateInstance().format(knowledgeOne.getLastUpdateDatetime())
		);
		assertEquals("henrique", knowledgeOne.getUserCreation().getUsername());
		assertEquals(
			DateFormat.getDateInstance().format(new Date()), 
			DateFormat.getDateInstance().format(knowledgeOne.getCreationDatetime())
		);
		
		assertEquals("TG Config", knowledgeOne.getTopics().get(0).getName());
		assertEquals("Instalação", knowledgeOne.getTopics().get(1).getName());
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
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/knowledge/1\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/knowledge/2\"}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/knowledge?page=0&size=10&sort=title,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":2,\"totalPages\":1,\"number\":0}"));
	}
	
}
