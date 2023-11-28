package br.com.ibpt.integrationtests.controllers.v2;

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

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.mocks.v2.SoftwareMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v2.SoftwareVO;
import br.com.ibpt.integrationtests.vo.wrappers.v2.WrapperSoftwareVO;
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
	
	private static String accessToken = "Bearer ";
	private static SoftwareVO software;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mock = new SoftwareMock();
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
				.setBasePath("/v2/sistema")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		software = mock.mockVO(0);
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
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
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", software.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SoftwareVO persistedCompany = mapper.readValue(content, SoftwareVO.class);
		
		assertEquals(software.getKey(), persistedCompany.getKey());
		assertEquals("Name0", persistedCompany.getName());
	}
	
	@Test
	@Order(3)
	public void testUpdateById() throws JsonMappingException, JsonProcessingException {
		software.setName(0 + "Name");
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", software.getKey())
				.body(software)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SoftwareVO updatedCompany = mapper.readValue(content, SoftwareVO.class);
		
		assertEquals(software.getKey(), updatedCompany.getKey());
		assertEquals("0Name", updatedCompany.getName());
	}
	
	@Test
	@Order(4)
	public void testDeleteById() {
		
		given().spec(specification)	
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", software.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		Integer pagina = 0;
		Integer tamanho = 10;
		String direcao = "desc";
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("pagina", pagina)
				.queryParam("tamanho", tamanho)
				.queryParam("direcao", direcao)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperSoftwareVO wrapper = mapper.readValue(content, WrapperSoftwareVO.class);
		
		List<SoftwareVO> resultList = wrapper.getEmbedded().getSoftwares();
		
		SoftwareVO softwareOne = resultList.get(0);
		
		assertEquals(2, softwareOne.getKey());
		assertEquals("Stac", softwareOne.getName());
	}
}
