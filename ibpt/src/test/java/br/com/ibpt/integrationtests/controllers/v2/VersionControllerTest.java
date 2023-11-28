package br.com.ibpt.integrationtests.controllers.v2;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.mocks.v1.VersionMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import br.com.ibpt.integrationtests.vo.wrappers.v2.WrapperVersionVO;
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
	private static ObjectMapper mapper;
	private static VersionMock mock;
	
	private static Date date;
	
	private static String accessToken = "Bearer ";
	private static VersionVO version;
	
	@BeforeAll
	public static void setup() throws ParseException {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		date = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
		
		mock = new VersionMock();
	}
	
	@Test
	@Order(0)
	public void authentication() {
		AccountCredentialsVO user = new AccountCredentialsVO("henrique", "he@01");
		
		accessToken += given() 
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
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v2/versao")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreateVersion() throws JsonMappingException, JsonProcessingException {
		VersionVO mockVO = mock.mockVO(2);
		mockVO.setEffectivePeriodUntil(date);
		
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
		
		VersionVO createdVersion = mapper.readValue(content, VersionVO.class);
		version = createdVersion;
		
		assertTrue(createdVersion.getKey() > 0);
		
		assertEquals("Name2", createdVersion.getName());
		assertEquals(date, createdVersion.getEffectivePeriodUntil());
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {

		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", version.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		VersionVO persistedVersion = mapper.readValue(content, VersionVO.class);

		assertEquals(version.getKey(), persistedVersion.getKey());
		assertEquals("Name2", persistedVersion.getName());
		assertEquals(date, persistedVersion.getEffectivePeriodUntil());
	}
	
	@Test
	@Order(3)
	public void testUpdateById() throws JsonMappingException, JsonProcessingException, ParseException {
		VersionVO mockVO = mock.mockVO();
		mockVO.setName(2 + "Name");
		date = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		mockVO.setEffectivePeriodUntil(date);
		
		var content = 
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", version.getKey())
				.body(mockVO)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
					
		VersionVO updatedVersion = mapper.readValue(content, VersionVO.class);
		version = updatedVersion;
		
		assertEquals(version.getKey(), updatedVersion.getKey());
		assertEquals("2Name", updatedVersion.getName());
		assertEquals(date, updatedVersion.getEffectivePeriodUntil());
	}
	
	@Test
	@Order(4)
	public void testDeleteById()  {
		
		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", version.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer page = 0;
		Integer size = 10;
		String direction = "desc";
		String orderBy = "vigenciaAte";
		String effectivePeriodYear = "2023";
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("pagina", page)
				.queryParam("tamanho", size)
				.queryParam("direcao", direction)
				.queryParam("ordenadoPor", orderBy)
				.queryParam("anoVigencia", effectivePeriodYear)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperVersionVO wrapper = mapper.readValue(content, WrapperVersionVO.class);
		
		List<VersionVO> resultList = wrapper.getEmbedded().getVersions();
		
		VersionVO softwareOne = resultList.get(0);
		
		assertEquals(3, softwareOne.getKey());
		assertEquals("23.2.D", softwareOne.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-31"), softwareOne.getEffectivePeriodUntil());
	}
}
