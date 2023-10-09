package br.com.ibpt.integrationtests.controllers.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;

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
import br.com.ibpt.integrationtests.mocks.v1.VersionMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.CompanyVO;
import br.com.ibpt.integrationtests.vo.v1.UpdateVO;
import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import br.com.ibpt.model.v1.IbptUpdate;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class IbptUpdateControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static CompanyMock inputCompany;
	private static VersionMock inputVersion;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		inputCompany = new CompanyMock();
		inputVersion = new VersionMock();
	}
	
	@Test
	@Order(1) 
	public void testCreateFirstCompany() throws JsonMappingException, JsonProcessingException {
		CompanyVO mockVO = inputCompany.mockVO(1);
		mockVO.setFkCompanySameDb(null);
		
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
		assertEquals(null, createdCompany.getFkCompanySameDb());
	}
	
	@Test
	@Order(2) 
	public void testCreateSecondCompany() throws JsonMappingException, JsonProcessingException {
		CompanyVO mockVO = inputCompany.mockVO(2);
		mockVO.setFkCompanySameDb(1);
		
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
		assertEquals(1, createdCompany.getFkCompanySameDb());
	}
	
	@Test
	@Order(3)
	public void createVersion() throws JsonMappingException, JsonProcessingException {
		VersionVO mockVO = inputVersion.mockVO(1);
		Date currentDate = new Date();
		mockVO.setEffectivePeriodUntil(currentDate);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/versao/novo")
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
		
		VersionVO createdVersion = objectMapper.readValue(content, VersionVO.class);
		
		assertNotNull(createdVersion);
		assertNotNull(createdVersion.getKey());
		assertNotNull(createdVersion.getName());
		assertNotNull(createdVersion.getEffectivePeriodUntil());
		
		assertTrue(createdVersion.getKey() > 0);
		
		assertEquals(1, createdVersion.getKey());
		assertEquals("Name1", createdVersion.getName());
		assertEquals(currentDate, createdVersion.getEffectivePeriodUntil());
	}
	
	/*
	
	// Implementar essa função no Controller
	@PostMapping
	public IbptUpdate create(@RequestBody IbptUpdate entity) {
		return ibptUpdateService.create(entity);
	}
	
	// Implementar essa função no Service
	public IbptUpdate create(IbptUpdate entity) {
		return ibptUpdateRepository.save(entity);
	}
	
	@Test
	@Order(4)
	public void mockFirstIbptUpdate() throws JsonMappingException, JsonProcessingException {
		IbptUpdate entity = new IbptUpdate();
		entity.setFkVersion(1);
		entity.setFkCompany(1);
		entity.setIsUpdated(false);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(entity)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, IbptUpdate.class);
		
		assertNotNull(result);
		
		assertEquals(1, result.getId());
		assertEquals(1, result.getFkVersion());
		assertEquals(1, result.getFkCompany());
		assertEquals(false, result.getIsUpdated());
	}
	
	@Test
	@Order(5)
	public void mockSecondIbptUpdate() throws JsonMappingException, JsonProcessingException {
		IbptUpdate entity = new IbptUpdate();
		entity.setFkVersion(1);
		entity.setFkCompany(2);
		entity.setIsUpdated(false);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(entity)
				.when()
				.post()
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		
		var result = objectMapper.readValue(content, IbptUpdate.class);
		
		assertNotNull(result);
		
		assertEquals(2, result.getId());
		assertEquals(1, result.getFkVersion());
		assertEquals(2, result.getFkCompany());
		assertEquals(false, result.getIsUpdated());
	}
	
	@Test
	@Order(6)
	public void testUpdate() {
		UpdateVO vo = new UpdateVO();
		vo.setId(1);
		vo.setValue(true);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(vo)
				.when()
					.put()
				.then()
					.statusCode(200);
		
		System.out.println(content.toString());
		assertNotNull(content);
	}
	
	@Test
	@Order(7)
	public void testFindCustom() throws JsonMappingException, JsonProcessingException {
		Boolean isUpdated = true;
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("estaAtualizado", isUpdated)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertNotNull(result);
		
		assertTrue(result.contains("isUpdated=" + isUpdated));
	}
	
	*/
}
