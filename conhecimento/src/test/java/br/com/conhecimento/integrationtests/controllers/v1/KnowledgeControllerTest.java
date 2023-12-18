package br.com.conhecimento.integrationtests.controllers.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
import br.com.conhecimento.integrationtests.mocks.v1.KnowledgeMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v1.KnowledgeVO;
import br.com.conhecimento.model.v1.SoftwareKnwl;
import br.com.conhecimento.model.v1.TopicKnwl;
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
		assertTrue(content.contains("\"VOList\":{\"href\":\"http://localhost:8888/v1/knowledge\"}"));
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
		assertTrue(content.contains("\"VOList\":{\"href\":\"http://localhost:8888/v1/knowledge\"}"));
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
		assertEquals("TG Config", updatedKnowledge.getTopics().get(0).getName());
		assertEquals("Instalação", updatedKnowledge.getTopics().get(1).getName());
		assertTrue(content.contains("\"VOList\":{\"href\":\"http://localhost:8888/v1/knowledge\"}"));
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
	void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var resultList = mapper.readValue(content, new TypeReference<List<KnowledgeVO>>() {});
	
		KnowledgeVO knowledgeOne = resultList.get(0);
		
		assertEquals(1, knowledgeOne.getKey());
		assertEquals("TG Config", knowledgeOne.getTitle());
		assertEquals("TG Config", knowledgeOne.getDescription());
		assertTrue(knowledgeOne.getContent().contains("<TGAPLICACAO name=\"Arca\" Caption=\"ARCA - Automação de Comércio e Indústria\">"));
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
		
		assertTrue(content.contains("{\"rel\":\"self\",\"href\":\"http://localhost:8888/v1/knowledge/1\"}"));
		assertTrue(content.contains("{\"rel\":\"self\",\"href\":\"http://localhost:8888/v1/knowledge/2\"}"));
	}
	
}
