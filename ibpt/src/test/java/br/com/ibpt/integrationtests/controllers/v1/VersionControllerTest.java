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
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.mocks.v1.VersionMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class VersionControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	private static VersionMock versionMock;
	
	private static String accessToken = "Bearer ";
	private static Integer idMax = 0;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		versionMock = new VersionMock();
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
	public void testCreateVersion() throws JsonMappingException, JsonProcessingException {
		VersionVO mockVO = versionMock.mockVO(2);
		
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
		
		assertEquals("Name2", createdVersion.getName());
		assertEquals(new Date(2), createdVersion.getEffectivePeriodUntil());
		
		idMax = createdVersion.getKey();
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {

		specification = new RequestSpecBuilder()
				.setBasePath("/v1/versao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", idMax)
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		VersionVO persistedVersion = objectMapper.readValue(content, VersionVO.class);

		assertNotNull(persistedVersion);
		assertNotNull(persistedVersion.getKey());
		assertNotNull(persistedVersion.getName());
		assertNotNull(persistedVersion.getEffectivePeriodUntil());
		
		assertEquals(idMax, persistedVersion.getKey());
		assertEquals("Name2", persistedVersion.getName());
		assertEquals(new Date(2), persistedVersion.getEffectivePeriodUntil());
	}
	
	@Test
	@Order(3)
	public void testFindCustom() throws JsonMappingException, JsonProcessingException {
		String effectivePeriodYear = "2023";
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/versao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("anoVigencia", effectivePeriodYear)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var result = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertNotNull(result);
		
		assertTrue(result.contains("effectivePeriodUntil=" + effectivePeriodYear));
	}
	
	@Test
	@Order(4)
	public void testFindByIdWithResourceNotFoundException() throws JsonMappingException, JsonProcessingException {
		Integer id = 10;
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/versao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
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
		assertTrue(content.contains("No records found for this id!"));
	}
	
	@Test
	@Order(5)
	public void testUpdateById() throws JsonMappingException, JsonProcessingException {
		VersionVO mockVO = versionMock.mockVO();
		mockVO.setName(2 + "Name");
		mockVO.setEffectivePeriodUntil(new Date(2));
		
		specification = new RequestSpecBuilder()
				.setBasePath("v1/versao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader("Authorization", accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", idMax)
				.body(mockVO)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
					
		VersionVO persistedVersion = objectMapper.readValue(content, VersionVO.class);
		
		assertNotNull(persistedVersion);
		assertEquals(idMax, persistedVersion.getKey());
		assertEquals("2Name", persistedVersion.getName());
		assertEquals(new Date(2), persistedVersion.getEffectivePeriodUntil());
	}
	
}
