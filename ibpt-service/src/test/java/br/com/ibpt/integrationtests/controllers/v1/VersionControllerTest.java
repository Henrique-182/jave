package br.com.ibpt.integrationtests.controllers.v1;

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

import br.com.ibpt.configs.v1.TestConfigs;
import br.com.ibpt.integrationtests.mocks.v1.VersionMock;
import br.com.ibpt.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import br.com.ibpt.integrationtests.vo.wrappers.v1.WrapperVersionVO;
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
	
	private static Date dateFrom;
	private static Date dateUntil;
	
	private static VersionVO version;
	
	@BeforeAll
	public static void setup() throws ParseException {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		dateFrom = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
		dateUntil = new SimpleDateFormat("yyyy-MM-dd").parse("2023-02-01");
		
		mock = new VersionMock();
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/version")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreateVersion() throws JsonMappingException, JsonProcessingException {
		VersionVO mockVO = mock.mockVO(2);
		mockVO.setEffectivePeriodFrom(dateFrom);
		mockVO.setEffectivePeriodUntil(dateUntil);
		
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
		
		assertEquals("Name2 ", createdVersion.getName());
		assertEquals(dateFrom, createdVersion.getEffectivePeriodFrom());
		assertEquals(dateUntil, createdVersion.getEffectivePeriodUntil());
		assertTrue(content.contains("\"_links\":{\"versionVOList\":{\"href\":\"http://localhost:8388/v1/version?page=0&size=10&direction=asc&sortBy=name\"}}}"));
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
		assertEquals("Name2 ", persistedVersion.getName());
		assertEquals(dateFrom, persistedVersion.getEffectivePeriodFrom());
		assertEquals(dateUntil, persistedVersion.getEffectivePeriodUntil());
		assertTrue(content.contains("\"_links\":{\"versionVOList\":{\"href\":\"http://localhost:8388/v1/version?page=0&size=10&direction=asc&sortBy=name\"}}}"));
	}
	
	@Test
	@Order(3)
	public void testUpdateById() throws JsonMappingException, JsonProcessingException, ParseException {
		VersionVO mockVO = mock.mockVO();
		mockVO.setName(2 + "Name");
		dateUntil = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
		mockVO.setEffectivePeriodFrom(dateFrom);
		mockVO.setEffectivePeriodUntil(dateUntil);
		
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
		assertEquals(dateFrom, updatedVersion.getEffectivePeriodFrom());
		assertEquals(dateUntil, updatedVersion.getEffectivePeriodUntil());
		assertTrue(content.contains("\"_links\":{\"versionVOList\":{\"href\":\"http://localhost:8388/v1/version?page=0&size=10&direction=asc&sortBy=name\"}}}"));
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
	public void testFindCustomPageable() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer page = 0;
		Integer size = 10;
		String direction = "desc";
		String sortBy = "effectivePeriodUntil";
		String effectivePeriodYear = "2023";
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.queryParam("effectivePeriodYear", effectivePeriodYear)
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
		
		assertEquals(4, softwareOne.getKey());
		assertEquals("23.2.E", softwareOne.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-01"), softwareOne.getEffectivePeriodFrom());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-11-30"), softwareOne.getEffectivePeriodUntil());
	}
	
	@Test
	@Order(6)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException  {
		Integer page = 0;
		Integer size = 10;
		String direction = "desc";
		String sortBy = "name";
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8388/v1/version/1\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8388/v1/version/2\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8388/v1/version/3\"}}"));
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8388/v1/version?direction=desc&sortBy=name&page=0&size=10\"}}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":6,\"totalPages\":1,\"number\":0}"));
	}
	
}
