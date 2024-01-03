package br.com.conhecimento.integrationtests.controllers.v2;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
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
import br.com.conhecimento.integrationtests.mocks.v2.SoftwareMock;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.integrationtests.vo.v2.SoftwareVO;
import br.com.conhecimento.integrationtests.vo.v2.TokenVO;
import br.com.conhecimento.integrationtests.vo.wrappers.v2.SoftwareWrapperVO;
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
				.setBasePath("/v2/software")
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
		
		assertEquals("henrique", createdSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(createdSoftware.getLastUpdateDatetime()));
		assertEquals("henrique", createdSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(createdSoftware.getCreationDatetime()));
		
		assertTrue(content.contains("\"softwareVOList\":{\"href\":\"http://localhost:8888/v2/software?page=0&size=10&sortBy=name&direction=asc\"}"));
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
		
		assertEquals("henrique", persistedSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(persistedSoftware.getLastUpdateDatetime()));
		assertEquals("henrique", persistedSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(persistedSoftware.getCreationDatetime()));
		
		assertTrue(content.contains("\"softwareVOList\":{\"href\":\"http://localhost:8888/v2/software?page=0&size=10&sortBy=name&direction=asc\"}"));
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
		
		SoftwareVO updatedSoftware = mapper.readValue(content, SoftwareVO.class);
		
		assertEquals(software.getKey(), updatedSoftware.getKey());
		assertEquals(software.getKey() + "Name", updatedSoftware.getName());
		
		assertEquals("henrique", updatedSoftware.getUserCreation().getUsername());
		assertEquals("henrique", updatedSoftware.getUserCreation().getUsername());
		assertTrue(updatedSoftware.getLastUpdateDatetime().after(updatedSoftware.getCreationDatetime()));
					
		assertTrue(content.contains("\"softwareVOList\":{\"href\":\"http://localhost:8888/v2/software?page=0&size=10&sortBy=name&direction=asc\"}"));
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
	void testFindPageable() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var wrapper = mapper.readValue(content, SoftwareWrapperVO.class);
		
		List<SoftwareVO> resultList = wrapper.getEmbedded().getSoftwares();
		
		SoftwareVO softwareOne = resultList.get(0);
		
		assertEquals(1, softwareOne.getKey());
		assertEquals("Esti", softwareOne.getName());
		
		assertEquals("henrique", softwareOne.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareOne.getLastUpdateDatetime()));
		assertEquals("henrique", softwareOne.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareOne.getCreationDatetime()));
		
		SoftwareVO softwareTwo = resultList.get(1);
		
		assertEquals(2, softwareTwo.getKey());
		assertEquals("Stac", softwareTwo.getName());
		
		assertEquals("henrique", softwareTwo.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareTwo.getLastUpdateDatetime()));
		assertEquals("henrique", softwareTwo.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareTwo.getCreationDatetime()));
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
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/software/1\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/software/2\"}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/software?page=0&size=10&sort=name,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":2,\"totalPages\":1,\"number\":0}"));
	}
	
}
