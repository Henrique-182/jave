package br.com.ibpt.integrationtests.controllers.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.mocks.v1.CompanyMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.CompanyVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class CompanyControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static CompanyMock input;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		input = new CompanyMock();
	}
	
	@Test
	@Order(1)
	public void testCreateFirstCompany() throws JsonMappingException, JsonProcessingException {
		CompanyVO mockVO = input.mockVO(1);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa/novo")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(mockVO)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO createdCompany = objectMapper.readValue(content, CompanyVO.class);
		
		assertNotNull(createdCompany);
		assertNotNull(createdCompany.getKey());
		
		assertTrue(createdCompany.getKey() > 0);
		
		assertEquals(1, createdCompany.getKey());
		assertEquals("11111111111111", createdCompany.getCnpj());
		assertEquals("Trade Name1", createdCompany.getTradeName());
		assertEquals("Business Name1", createdCompany.getBusinessName());
		assertEquals("Esti", createdCompany.getSoftware());
		assertEquals(false, createdCompany.getHaveAuthorization());
		assertEquals("111111111", createdCompany.getConnection());
		assertEquals("Observation1", createdCompany.getObservation());
		assertEquals(false, createdCompany.getIsActive());
		assertEquals(1, createdCompany.getFkCompanySameDb());
	}
	
	@Test
	@Order(2)
	public void testCreateOtherCompany() throws JsonMappingException, JsonProcessingException {
		CompanyVO mockVO = input.mockVO(2);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa/novo")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(mockVO)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		
		CompanyVO createdCompany = objectMapper.readValue(content, CompanyVO.class);
		
		assertNotNull(createdCompany);
		assertNotNull(createdCompany.getKey());
		
		assertTrue(createdCompany.getKey() > 0);
		
		assertEquals(2, createdCompany.getKey());
		assertEquals("22222222222222", createdCompany.getCnpj());
		assertEquals("Trade Name2", createdCompany.getTradeName());
		assertEquals("Business Name2", createdCompany.getBusinessName());
		assertEquals("Stac", createdCompany.getSoftware());
		assertEquals(true, createdCompany.getHaveAuthorization());
		assertEquals("222222222", createdCompany.getConnection());
		assertEquals("Observation2", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		assertEquals(4, createdCompany.getFkCompanySameDb());
	}
	
	@Test
	@Order(3)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		Integer id = 1;
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", id)
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO persistedCompany = objectMapper.readValue(content, CompanyVO.class);
		
		assertNotNull(persistedCompany);
		
		assertEquals(id, persistedCompany.getKey());
		assertEquals("11111111111111", persistedCompany.getCnpj());
		assertEquals("Trade Name1", persistedCompany.getTradeName());
		assertEquals("Business Name1", persistedCompany.getBusinessName());
		assertEquals("Esti", persistedCompany.getSoftware());
		assertEquals(false, persistedCompany.getHaveAuthorization());
		assertEquals("111111111", persistedCompany.getConnection());
		assertEquals("Observation1", persistedCompany.getObservation());
		assertEquals(false, persistedCompany.getIsActive());
		assertEquals(1, persistedCompany.getFkCompanySameDb());
	}
	
	@Test
	@Order(4)
	public void testFindByIdWithResourceNotFoundException() {
		Integer id = 10;
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", id)
				.when()
					.get("{id}")
				.then()
					.statusCode(404)
				.extract()
					.body()
					.asString();
		
		assertNotNull(content);
		assertTrue(content.contains("No records found for this ID!"));
	}
	
	@Test
	@Order(5)
	public void testFindCustom() throws JsonMappingException, JsonProcessingException {
		String sistema = "Stac";
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("sistema", sistema)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var resultList = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertTrue(resultList.contains("software=" + sistema));
	}
	
	@Test
	@Order(6)
	public void testUpdateById() throws JsonMappingException, JsonProcessingException {
		Integer id = 1;
		CompanyVO mockVO = new CompanyVO();
		mockVO.setTradeName(id + "Trade Name");
		mockVO.setBusinessName(id + "Business Name");
		mockVO.setSoftware("Stac");
		mockVO.setHaveAuthorization(true);
		mockVO.setConnection("" + id + id + id);
		mockVO.setObservation(id + "Observation");
		mockVO.setIsActive(true);
		mockVO.setFkCompanySameDb(id + id);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", id)
				.body(mockVO)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		CompanyVO persistedCompany = objectMapper.readValue(content, CompanyVO.class);
		
		assertEquals(id, persistedCompany.getKey());
		assertEquals("11111111111111", persistedCompany.getCnpj());
		assertEquals("1Trade Name", persistedCompany.getTradeName());
		assertEquals("1Business Name", persistedCompany.getBusinessName());
		assertEquals("Stac", persistedCompany.getSoftware());
		assertEquals(true, persistedCompany.getHaveAuthorization());
		assertEquals("111", persistedCompany.getConnection());
		assertEquals("1Observation", persistedCompany.getObservation());
		assertEquals(true, persistedCompany.getIsActive());
		assertEquals(2, persistedCompany.getFkCompanySameDb());
	}
}
