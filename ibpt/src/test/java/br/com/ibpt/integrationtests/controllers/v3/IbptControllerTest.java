package br.com.ibpt.integrationtests.controllers.v3;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import br.com.ibpt.integrationtests.mocks.v2.CompanyMock;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import br.com.ibpt.integrationtests.vo.v2.CompanyActiveVO;
import br.com.ibpt.integrationtests.vo.v2.CompanyVO;
import br.com.ibpt.integrationtests.vo.v2.IbptUpdateVO;
import br.com.ibpt.integrationtests.vo.v2.IbptVO;
import br.com.ibpt.integrationtests.vo.wrappers.v2.WrapperIbptVO;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.model.v2.CompanyIbpt;
import br.com.ibpt.model.v2.CompanySoftware;
import br.com.ibpt.model.v2.CompanySoftwareIbpt;
import br.com.ibpt.model.v2.SoftwareIbpt;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class IbptControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static CompanyMock companyMock;
	private static VersionMock versionMock;
	
	private static String accessToken = "Bearer ";
	
	private static IbptVO ibpt;
	private static IbptVO ibpt2;
	private static CompanyVO firstCompany;
	private static CompanyVO secondCompany;
	private static VersionVO version;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		companyMock = new CompanyMock();
		versionMock = new VersionMock();
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
				.setPort(TestConfigs.SERVER_PORT)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, accessToken)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1) 
	public void testCreateFirstCompany() throws JsonMappingException, JsonProcessingException {
		firstCompany = companyMock.mockVO(4);
		
		var content = given().spec(specification)	
				.basePath("/v3/company")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(firstCompany)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO createdCompany = mapper.readValue(content, CompanyVO.class);
		firstCompany = createdCompany;
		
		assertTrue(createdCompany.getKey() > 0);
		
		assertEquals("44444444444444", createdCompany.getCnpj());
		assertEquals("Trade Name4", createdCompany.getTradeName());
		assertEquals("Business Name4", createdCompany.getBusinessName());
		assertEquals("Observation4", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		
		List<CompanySoftware> createdCompanySoftware = createdCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = createdCompanySoftware.get(0);
		
		assertTrue(companySoftwareOne.getId() > 0);
		
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection4", companySoftwareOne.getConnection());
		assertEquals("Observation4", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = createdCompanySoftware.get(1);
		
		assertTrue(companySoftwareTwo.getId() > 0);
		
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection4", companySoftwareTwo.getConnection());
		assertEquals("Observation4", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(2) 
	public void testCreateSecondCompany() throws JsonMappingException, JsonProcessingException {
		secondCompany = companyMock.mockVO(6);
		secondCompany.getSoftwares().get(0).setFkCompanySoftwareSameDb(firstCompany.getSoftwares().get(0).getId());
		
		var content = given().spec(specification)	
				.basePath("/v3/company")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(secondCompany)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		CompanyVO createdCompany = mapper.readValue(content, CompanyVO.class);
		secondCompany = createdCompany;
		
		assertTrue(createdCompany.getKey() > 0);
		
		assertEquals("66666666666666", createdCompany.getCnpj());
		assertEquals("Trade Name6", createdCompany.getTradeName());
		assertEquals("Business Name6", createdCompany.getBusinessName());
		assertEquals("Observation6", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		
		List<CompanySoftware> createdCompanySoftware = createdCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = createdCompanySoftware.get(0);
		
		assertTrue(companySoftwareOne.getId() > 0);
		
		assertEquals("Stac", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection6", companySoftwareOne.getConnection());
		assertEquals("Observation6", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(firstCompany.getSoftwares().get(0).getId(), companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = createdCompanySoftware.get(1);
		
		assertTrue(companySoftwareTwo.getId() > 0);
		
		assertEquals("Stac", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection6", companySoftwareTwo.getConnection());
		assertEquals("Observation6", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	@Order(3)
	public void testCreateVersion() throws JsonMappingException, JsonProcessingException, ParseException {
		version = versionMock.mockVO(1);
		version.setEffectivePeriodUntil(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"));
		
		var content = given().spec(specification)
				.basePath("/v3/version")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(version)
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
		
		assertEquals("Name1", createdVersion.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01"), createdVersion.getEffectivePeriodUntil());
	}
	
	@Test
	@Order(4)
	public void testCreateIbpt() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer versionId = version.getKey();
		
		given().spec(specification)
			.basePath("/v3/ibpt")
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("versionId", versionId)
			.when()
				.post("{versionId}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	public void testFindAllBeforeUpdated() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer page = 0;
		Integer size = 10;
		String direction = "asc";
		String sortBy = "empresaCnpj";
		String companyCnpj = firstCompany.getCnpj();
		String versionNome = version.getName();
		
		var content = given().spec(specification)
				.basePath("/v3/ibpt")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.queryParam("companyCnpj", companyCnpj)
				.queryParam("versionNome", versionNome)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperIbptVO wrapper = mapper.readValue(content, WrapperIbptVO.class);
		
		List<IbptVO> resultList = wrapper.getEmbedded().getIbpts();
		
		IbptVO ibptOne = resultList.get(0);
		ibpt = ibptOne;
		
		assertTrue(ibptOne.getKey() > 0);
		assertFalse(ibptOne.getIsUpdated());
		
		Version versionIbpt = ibptOne.getVersion();
		
		assertEquals(version.getKey(), versionIbpt.getId());
		assertEquals(version.getName(), versionIbpt.getName());
		assertEquals(version.getEffectivePeriodUntil(), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibptOne.getCompanySoftware();
		
		assertEquals(firstCompany.getSoftwares().get(0).getId(), companySoftwareIbpt.getId());
		assertEquals(firstCompany.getSoftwares().get(0).getType(), companySoftwareIbpt.getType());
		assertEquals(firstCompany.getSoftwares().get(0).getHaveAuthorization(), companySoftwareIbpt.getHaveAuthorization());
		assertEquals(firstCompany.getSoftwares().get(0).getConnection(), companySoftwareIbpt.getConnection());
		assertEquals(firstCompany.getSoftwares().get(0).getObservation(), companySoftwareIbpt.getObservation());
		assertEquals(firstCompany.getSoftwares().get(0).getIsActive(), companySoftwareIbpt.getIsActive());
		assertEquals(firstCompany.getSoftwares().get(0).getFkCompanySoftwareSameDb(), companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(firstCompany.getKey(), companyIbpt.getId());
		assertEquals(firstCompany.getTradeName(), companyIbpt.getTradeName());
		assertEquals(firstCompany.getBusinessName(), companyIbpt.getBusinessName());
		assertEquals(firstCompany.getObservation(), companyIbpt.getObservation());
		assertEquals(firstCompany.getIsActive(), companyIbpt.getIsActive());
	}
	
	@Test
	@Order(6)
	public void testFindAllBeforeUpdated2() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer page = 0;
		Integer size = 10;
		String direction = "asc";
		String sortBy = "empresaCnpj";
		String companyCnpj = secondCompany.getCnpj();
		String versionNome = version.getName();
		
		var content = given().spec(specification)
				.basePath("/v3/ibpt")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.queryParam("companyCnpj", companyCnpj)
				.queryParam("versionNome", versionNome)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperIbptVO wrapper = mapper.readValue(content, WrapperIbptVO.class);
		
		List<IbptVO> resultList = wrapper.getEmbedded().getIbpts();
		
		IbptVO ibptOne = resultList.get(0);
		ibpt2 = ibptOne;
		
		assertTrue(ibptOne.getKey() > 0);
		assertFalse(ibptOne.getIsUpdated());
		
		Version versionIbpt = ibptOne.getVersion();
		
		assertEquals(version.getKey(), versionIbpt.getId());
		assertEquals(version.getName(), versionIbpt.getName());
		assertEquals(version.getEffectivePeriodUntil(), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibptOne.getCompanySoftware();
		
		assertEquals(secondCompany.getSoftwares().get(0).getId(), companySoftwareIbpt.getId());
		assertEquals(secondCompany.getSoftwares().get(0).getType(), companySoftwareIbpt.getType());
		assertEquals(secondCompany.getSoftwares().get(0).getHaveAuthorization(), companySoftwareIbpt.getHaveAuthorization());
		assertEquals(secondCompany.getSoftwares().get(0).getConnection(), companySoftwareIbpt.getConnection());
		assertEquals(secondCompany.getSoftwares().get(0).getObservation(), companySoftwareIbpt.getObservation());
		assertEquals(secondCompany.getSoftwares().get(0).getIsActive(), companySoftwareIbpt.getIsActive());
		assertEquals(secondCompany.getSoftwares().get(0).getFkCompanySoftwareSameDb(), companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(secondCompany.getKey(), companyIbpt.getId());
		assertEquals(secondCompany.getTradeName(), companyIbpt.getTradeName());
		assertEquals(secondCompany.getBusinessName(), companyIbpt.getBusinessName());
		assertEquals(secondCompany.getObservation(), companyIbpt.getObservation());
		assertEquals(secondCompany.getIsActive(), companyIbpt.getIsActive());
	}
	
	@Test
	@Order(7)
	public void testUpdate() {
		IbptUpdateVO data = new IbptUpdateVO();
		data.setKey(ibpt.getKey());
		data.setValue(true);
		
		given().spec(specification)
			.basePath("/v3/ibpt")
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(data)
			.when()
				.patch()
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(8)
	public void testFindAllAfterUpdated() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer page = 0;
		Integer size = 10;
		String direction = "asc";
		String sortBy = "companyCnpj";
		String companyCnpj = firstCompany.getCnpj();
		String versionNome = version.getName();
		
		var content = given().spec(specification)
				.basePath("/v3/ibpt")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.queryParam("companyCnpj", companyCnpj)
				.queryParam("versionNome", versionNome)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperIbptVO wrapper = mapper.readValue(content, WrapperIbptVO.class);
		
		List<IbptVO> resultList = wrapper.getEmbedded().getIbpts();
		
		IbptVO ibptOne = resultList.get(0);
		
		assertTrue(ibptOne.getKey() > 0);
		assertTrue(ibptOne.getIsUpdated());
		
		Version versionIbpt = ibptOne.getVersion();
		
		assertEquals(version.getKey(), versionIbpt.getId());
		assertEquals(version.getName(), versionIbpt.getName());
		assertEquals(version.getEffectivePeriodUntil(), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibptOne.getCompanySoftware();
		
		assertEquals(firstCompany.getSoftwares().get(0).getId(), companySoftwareIbpt.getId());
		assertEquals(firstCompany.getSoftwares().get(0).getType(), companySoftwareIbpt.getType());
		assertEquals(firstCompany.getSoftwares().get(0).getHaveAuthorization(), companySoftwareIbpt.getHaveAuthorization());
		assertEquals(firstCompany.getSoftwares().get(0).getConnection(), companySoftwareIbpt.getConnection());
		assertEquals(firstCompany.getSoftwares().get(0).getObservation(), companySoftwareIbpt.getObservation());
		assertEquals(firstCompany.getSoftwares().get(0).getIsActive(), companySoftwareIbpt.getIsActive());
		assertEquals(firstCompany.getSoftwares().get(0).getFkCompanySoftwareSameDb(), companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(firstCompany.getKey(), companyIbpt.getId());
		assertEquals(firstCompany.getTradeName(), companyIbpt.getTradeName());
		assertEquals(firstCompany.getBusinessName(), companyIbpt.getBusinessName());
		assertEquals(firstCompany.getObservation(), companyIbpt.getObservation());
		assertEquals(firstCompany.getIsActive(), companyIbpt.getIsActive());
	}
	
	@Test
	@Order(9)
	public void testFindAllAfterUpdated2() throws JsonMappingException, JsonProcessingException, ParseException {
		Integer page = 0;
		Integer size = 10;
		String direction = "asc";
		String sortBy = "empresaCnpj";
		String companyCnpj = secondCompany.getCnpj();
		String versionNome = version.getName();
		
		var content = given().spec(specification)
				.basePath("/v3/ibpt")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("page", page)
				.queryParam("size", size)
				.queryParam("direction", direction)
				.queryParam("sortBy", sortBy)
				.queryParam("companyCnpj", companyCnpj)
				.queryParam("versionNome", versionNome)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperIbptVO wrapper = mapper.readValue(content, WrapperIbptVO.class);
		
		List<IbptVO> resultList = wrapper.getEmbedded().getIbpts();
		
		IbptVO ibptOne = resultList.get(0);
		ibpt2 = ibptOne;
		
		assertTrue(ibptOne.getKey() > 0);
		assertTrue(ibptOne.getIsUpdated());
		
		Version versionIbpt = ibptOne.getVersion();
		
		assertEquals(version.getKey(), versionIbpt.getId());
		assertEquals(version.getName(), versionIbpt.getName());
		assertEquals(version.getEffectivePeriodUntil(), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibptOne.getCompanySoftware();
		
		assertEquals(secondCompany.getSoftwares().get(0).getId(), companySoftwareIbpt.getId());
		assertEquals(secondCompany.getSoftwares().get(0).getType(), companySoftwareIbpt.getType());
		assertEquals(secondCompany.getSoftwares().get(0).getHaveAuthorization(), companySoftwareIbpt.getHaveAuthorization());
		assertEquals(secondCompany.getSoftwares().get(0).getConnection(), companySoftwareIbpt.getConnection());
		assertEquals(secondCompany.getSoftwares().get(0).getObservation(), companySoftwareIbpt.getObservation());
		assertEquals(secondCompany.getSoftwares().get(0).getIsActive(), companySoftwareIbpt.getIsActive());
		assertEquals(secondCompany.getSoftwares().get(0).getFkCompanySoftwareSameDb(), companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(secondCompany.getKey(), companyIbpt.getId());
		assertEquals(secondCompany.getTradeName(), companyIbpt.getTradeName());
		assertEquals(secondCompany.getBusinessName(), companyIbpt.getBusinessName());
		assertEquals(secondCompany.getObservation(), companyIbpt.getObservation());
		assertEquals(secondCompany.getIsActive(), companyIbpt.getIsActive());
	}
	
	@Test
	@Order(10)
	public void testDeleteById() {
		
		given().spec(specification)
			.basePath("/v3/ibpt")
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", ibpt.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
		
		given().spec(specification)
			.basePath("/v3/ibpt")
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", ibpt2.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(11)
	public void testUpdateCompanyIsActiveById() throws JsonMappingException, JsonProcessingException {
		CompanyActiveVO data = new CompanyActiveVO();
		data.setId(firstCompany.getKey());
		data.setValue(false);
		
		given().spec(specification)	
		.basePath("/v3/company")
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(data)
			.when()
				.patch()
			.then()
				.statusCode(200);
		
		data.setId(secondCompany.getKey());
		data.setValue(false);
		
		given().spec(specification)	
			.basePath("/v3/company")
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.body(data)
			.when()
				.patch()
			.then()
				.statusCode(200);
	}
	
	@Test
	@Order(12)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/v3/ibpt")
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
	@Order(13)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException  {
		Integer page = 5;
		Integer size = 10;
		String direction = "asc";
		
		var content = given().spec(specification)	
				.basePath("/v3/ibpt")
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
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/ibpt/51\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/ibpt/52\"}}"));
		
		assertTrue(content.contains("\"first\":{\"href\":\"http://localhost:8888/v3/ibpt?direction=asc&page=0&size=10\"}"));
		assertTrue(content.contains("\"prev\":{\"href\":\"http://localhost:8888/v3/ibpt?direction=asc&page=4&size=10\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v3/ibpt?direction=asc&page=5&size=10\"}"));
		assertTrue(content.contains("\"next\":{\"href\":\"http://localhost:8888/v3/ibpt?direction=asc&page=6&size=10\"}"));
		assertTrue(content.contains("\"last\":{\"href\":\"http://localhost:8888/v3/ibpt?direction=asc&page=63&size=10\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":632,\"totalPages\":64,\"number\":5"));
	}
	
	
}
