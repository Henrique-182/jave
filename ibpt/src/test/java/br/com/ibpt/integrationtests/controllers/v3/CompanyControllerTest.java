package br.com.ibpt.integrationtests.controllers.v3;

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
import br.com.ibpt.integrationtests.mocks.v2.CompanyMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v2.CompanyActiveVO;
import br.com.ibpt.integrationtests.vo.v2.CompanyVO;
import br.com.ibpt.integrationtests.vo.wrappers.v2.WrapperCompanyVO;
import br.com.ibpt.model.v2.CompanySoftware;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class CompanyControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	private static CompanyMock mock;
	
	private static String accessToken = "Bearer ";
	private static CompanyVO company;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		mock = new CompanyMock();
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
				.setBasePath("/v3/company")
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
				
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		company = mock.mockVO(2);
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(company)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO createdCompany = mapper.readValue(content, CompanyVO.class);
		company = createdCompany;
		
		assertTrue(createdCompany.getKey() > 0);
		
		assertEquals("22222222222222", createdCompany.getCnpj());
		assertEquals("Trade Name2", createdCompany.getTradeName());
		assertEquals("Business Name2", createdCompany.getBusinessName());
		assertEquals("Observation2", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		
		List<CompanySoftware> createdCompanySoftware = createdCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = createdCompanySoftware.get(0);
		
		assertTrue(companySoftwareOne.getId() > 0);
		
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = createdCompanySoftware.get(1);
		
		assertTrue(companySoftwareTwo.getId() > 0);
		
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(2)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", company.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO persistedCompany = mapper.readValue(content, CompanyVO.class);
		
		assertEquals(company.getKey(), persistedCompany.getKey());
		assertEquals("22222222222222", persistedCompany.getCnpj());
		assertEquals("Trade Name2", persistedCompany.getTradeName());
		assertEquals("Business Name2", persistedCompany.getBusinessName());
		assertEquals("Observation2", persistedCompany.getObservation());
		assertEquals(true, persistedCompany.getIsActive());
		
		List<CompanySoftware> createdCompanySoftware = persistedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = createdCompanySoftware.get(0);
		
		assertEquals(company.getSoftwares().get(0).getId(), companySoftwareOne.getId());
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = createdCompanySoftware.get(1);
		
		assertEquals(company.getSoftwares().get(1).getId(), companySoftwareTwo.getId());
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(3)
	public void testUpdateById() throws JsonMappingException, JsonProcessingException {
		company.setBusinessName(2 + "Business Name");
		company.setTradeName(2 + "Trade Name");
		company.setObservation(2 + "Observation");
		company.getSoftwares().get(0).setConnection(2 + "Connection");
		company.getSoftwares().get(0).setObservation(2 + "Observation");
		company.getSoftwares().get(1).setConnection(2 + "Connection");
		company.getSoftwares().get(1).setObservation(2 + "Observation");
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", company.getKey())
				.body(company)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO updatedCompany = mapper.readValue(content, CompanyVO.class);
		company = updatedCompany;
		
		assertEquals(company.getKey(), updatedCompany.getKey());
		assertEquals("22222222222222", updatedCompany.getCnpj());
		assertEquals("2Trade Name", updatedCompany.getTradeName());
		assertEquals("2Business Name", updatedCompany.getBusinessName());
		assertEquals("2Observation", updatedCompany.getObservation());
		assertEquals(true, updatedCompany.getIsActive());
		
		List<CompanySoftware> updatedCompanySoftware = updatedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = updatedCompanySoftware.get(0);
		
		assertEquals(company.getSoftwares().get(0).getId(), companySoftwareOne.getId());
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("2Connection", companySoftwareOne.getConnection());
		assertEquals("2Observation", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = updatedCompanySoftware.get(1);
		
		assertEquals(company.getSoftwares().get(1).getId(), companySoftwareTwo.getId());
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("2Connection", companySoftwareTwo.getConnection());
		assertEquals("2Observation", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(4)
	public void testUpdateCompanySoftwareIsActiveById() throws JsonMappingException, JsonProcessingException {
		CompanyActiveVO data = new CompanyActiveVO();
		data.setId(company.getSoftwares().get(0).getId());
		data.setValue(false);
		
		given().spec(specification)	
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(data)
			.when()
				.patch("/software")
			.then()
				.statusCode(200);
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", company.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO updatedCompany = mapper.readValue(content, CompanyVO.class);
		company = updatedCompany;
		
		assertTrue(updatedCompany.getKey() > 0);
		
		assertEquals("22222222222222", updatedCompany.getCnpj());
		assertEquals("2Trade Name", updatedCompany.getTradeName());
		assertEquals("2Business Name", updatedCompany.getBusinessName());
		assertEquals("2Observation", updatedCompany.getObservation());
		assertEquals(true, updatedCompany.getIsActive());
		
		List<CompanySoftware> updatedCompanySoftware = updatedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = updatedCompanySoftware.get(0);
		
		assertTrue(companySoftwareOne.getId() > 0);
		
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("2Connection", companySoftwareOne.getConnection());
		assertEquals("2Observation", companySoftwareOne.getObservation());
		assertEquals(false, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = updatedCompanySoftware.get(1);
		
		assertTrue(companySoftwareTwo.getId() > 0);
		
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("2Connection", companySoftwareTwo.getConnection());
		assertEquals("2Observation", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(5)
	public void testUpdateCompanyIsActiveById() throws JsonMappingException, JsonProcessingException {
		CompanyActiveVO data = new CompanyActiveVO();
		data.setId(company.getKey());
		data.setValue(false);
		
		given().spec(specification)	
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(data)
			.when()
				.patch("")
			.then()
				.statusCode(200);
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", company.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO updatedCompany = mapper.readValue(content, CompanyVO.class);
		company = updatedCompany;
		
		assertTrue(updatedCompany.getKey() > 0);
		
		assertEquals("22222222222222", updatedCompany.getCnpj());
		assertEquals("2Trade Name", updatedCompany.getTradeName());
		assertEquals("2Business Name", updatedCompany.getBusinessName());
		assertEquals("2Observation", updatedCompany.getObservation());
		assertEquals(false, updatedCompany.getIsActive());
		
		List<CompanySoftware> updatedCompanySoftware = updatedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = updatedCompanySoftware.get(0);
		
		assertTrue(companySoftwareOne.getId() > 0);
		
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("2Connection", companySoftwareOne.getConnection());
		assertEquals("2Observation", companySoftwareOne.getObservation());
		assertEquals(false, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = updatedCompanySoftware.get(1);
		
		assertTrue(companySoftwareTwo.getId() > 0);
		
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("2Connection", companySoftwareTwo.getConnection());
		assertEquals("2Observation", companySoftwareTwo.getObservation());
		assertEquals(false, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(6)
	public void testDeleteById() {
		
		given().spec(specification)	
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", company.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(7)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		Integer page = 0;
		Integer size = 10;
		String direction = "asc";
		String sortBy = "cnpj";
		String name = "rai";
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.queryParam("name", name)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperCompanyVO wrapper = mapper.readValue(content, WrapperCompanyVO.class);
		
		List<CompanyVO> resultList = wrapper.getEmbedded().getCompanies();
		
		CompanyVO persistedCompany = resultList.get(0);
		
		assertEquals(140, persistedCompany.getKey());
		assertEquals("14767260000100", persistedCompany.getCnpj());
		assertEquals("MORAIS DISTRIBUIDORA DE MATERIAIS PARA CONSTRUCAO", persistedCompany.getTradeName());
		assertEquals("MORAIS DISTRIBUIDORA DE MATERIAIS PARA CONSTRUCAO LTDA - ME", persistedCompany.getBusinessName());
		assertEquals("", persistedCompany.getObservation());
		assertEquals(true, persistedCompany.getIsActive());
		assertTrue(content.toString().contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/company/140\"}}"));
		
		List<CompanySoftware> persistedCompanySoftware = persistedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = persistedCompanySoftware.get(0);
		
		assertEquals(140, companySoftwareOne.getId());
		assertEquals("Esti", companySoftwareOne.getSoftware().getName());
		assertEquals("FISCAL", companySoftwareOne.getType());
		assertEquals(false, companySoftwareOne.getHaveAuthorization());
		assertEquals("741878875", companySoftwareOne.getConnection());
		assertEquals("", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(8)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/v3/company")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		given().spec(specificationWithoutToken)	
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.when()
				.get()
			.then()
				.statusCode(403);
	}
	
	@Test
	@Order(9)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException  {
		Integer page = 10;
		Integer size = 10;
		String direction = "asc";
		
		var content = given().spec(specification)	
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/company/112\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/company/140\"}}"));
		
		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/v3/company?direction=asc&page=0&size=10\"}"));
		assertTrue(content.contains("\"prev\":{\"href\":\"http://localhost:8888/v3/company?direction=asc&page=9&size=10\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v3/company?direction=asc&page=10&size=10\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/v3/company?direction=asc&page=11&size=10\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/v3/company?direction=asc&page=15&size=10\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":160,\"totalPages\":16,\"number\":10}"));
	}
}
