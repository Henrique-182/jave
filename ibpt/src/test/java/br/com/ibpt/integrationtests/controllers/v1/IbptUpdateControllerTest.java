package br.com.ibpt.integrationtests.controllers.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.mocks.v1.CompanyMock;
import br.com.ibpt.integrationtests.mocks.v1.VersionMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.CompanyVO;
import br.com.ibpt.integrationtests.vo.v1.IbptUpdateVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v1.UpdateVO;
import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class IbptUpdateControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static CompanyMock inputCompany;
	private static VersionMock inputVersion;
	
	private static String accessToken = "Bearer ";
	private static Integer idCompany = 0;
	private static Integer idIbptUpdate = 0;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		inputCompany = new CompanyMock();
		inputVersion = new VersionMock();
	}
	
	@Test
	@Order(0)
	public void authorization() {
		AccountCredentialsVO user = new AccountCredentialsVO("Henrique", "he@01");
		
		accessToken = accessToken +
				given()
				.basePath("/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class)
					.getAccessToken();
	}
	
	@Test
	@Order(1) 
	public void testCreateCompany() throws JsonMappingException, JsonProcessingException {
		CompanyVO mockVO = inputCompany.mockVO(1);
		mockVO.setIsActive(true);
		mockVO.setFkCompanySameDb(null);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa/novo")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
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
		
		assertEquals("11111111111111", createdCompany.getCnpj());
		assertEquals("Trade Name1", createdCompany.getTradeName());
		assertEquals("Business Name1", createdCompany.getBusinessName());
		assertEquals("Esti", createdCompany.getSoftware());
		assertEquals(false, createdCompany.getHaveAuthorization());
		assertEquals("111111111", createdCompany.getConnection());
		assertEquals("Observation1", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		assertEquals(null, createdCompany.getFkCompanySameDb());
		
		idCompany = createdCompany.getKey();
	}
	
	@Test
	@Order(2) 
	public void testCreateCompanySameDb() throws JsonMappingException, JsonProcessingException {
		CompanyVO mockVO = inputCompany.mockVO(2);
		mockVO.setFkCompanySameDb(idCompany);
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/empresa/novo")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
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
		
		assertEquals("22222222222222", createdCompany.getCnpj());
		assertEquals("Trade Name2", createdCompany.getTradeName());
		assertEquals("Business Name2", createdCompany.getBusinessName());
		assertEquals("Stac", createdCompany.getSoftware());
		assertEquals(true, createdCompany.getHaveAuthorization());
		assertEquals("222222222", createdCompany.getConnection());
		assertEquals("Observation2", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		assertEquals(idCompany, createdCompany.getFkCompanySameDb());
	}
	
	@Test
	@Order(3)
	public void testCreateVersion() throws JsonMappingException, JsonProcessingException {
		VersionVO mockVO = inputVersion.mockVO(1);
		Date currentDate = new Date();
		mockVO.setEffectivePeriodUntil(currentDate);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/versao/novo")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
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
		
		assertEquals("Name1", createdVersion.getName());
		assertEquals(currentDate, createdVersion.getEffectivePeriodUntil());
	}
	
	
	@Test
	@Order(4)
	public void testFindCustomBeforeUpdated() throws JsonMappingException, JsonProcessingException {
		String companyCnpj = "11111111111111";
		String versionName = "Name1";
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("empresaCnpj", companyCnpj)
				.queryParam("versaoNome", versionName)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, new TypeReference<List<IbptUpdateVO>>() {});
		
		assertNotNull(result);
		
		assertTrue(
			  result.get(0).getCompanyCnpj().equals(companyCnpj)
			&& result.get(0).getVersionName().equals(versionName)
			&& result.get(0).getIsUpdated().equals(false)
		);
		
		idIbptUpdate = result.get(0).getKey();
	}
	
	@Test
	@Order(5)
	public void testFindCustomBeforeUpdated2() throws JsonMappingException, JsonProcessingException {
		String companyCnpj = "22222222222222";
		String versionName = "Name1";
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("empresaCnpj", companyCnpj)
				.queryParam("versaoNome", versionName)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertNotNull(result);
		
		assertTrue(
				result.contains("companyCnpj=" + companyCnpj) 
			 && result.contains("versionName=" + versionName)
			 && result.contains("isUpdated=false")
		);
		
	}
	
	@Test
	@Order(6)
	public void testUpdate() {
		UpdateVO vo = new UpdateVO();
		vo.setId(idIbptUpdate);
		vo.setValue(true);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
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
		
		assertNotNull(content);
	}
	
	@Test
	@Order(7)
	public void testFindCustomAfterUpdated() throws JsonMappingException, JsonProcessingException {
		String companyCnpj = "11111111111111";
		String versionName = "Name1";
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("empresaCnpj", companyCnpj)
				.queryParam("versaoNome", versionName)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertNotNull(result);
		
		assertTrue(
				result.contains("companyCnpj=" + companyCnpj) 
			 && result.contains("versionName=" + versionName)
			 && result.contains("isUpdated=true")
		);
	}
	
	@DirtiesContext
	@Test
	@Order(8)
	public void testFindCustomAfterUpdated2() throws JsonMappingException, JsonProcessingException {
		String companyCnpj = "22222222222222";
		String versionName = "Name1";
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/atualizacao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("empresaCnpj", companyCnpj)
				.queryParam("versaoNome", versionName)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertNotNull(result);
		
		assertTrue(
				result.contains("companyCnpj=" + companyCnpj) 
			 && result.contains("versionName=" + versionName)
			 && result.contains("isUpdated=true")
		);
	}
}
