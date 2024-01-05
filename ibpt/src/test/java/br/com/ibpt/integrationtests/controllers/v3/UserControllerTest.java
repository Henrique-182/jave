package br.com.ibpt.integrationtests.controllers.v3;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v3.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v3.TokenVO;
import br.com.ibpt.integrationtests.vo.v3.UserVO;
import br.com.ibpt.integrationtests.vo.wrappers.v2.WrapperUserVO;
import br.com.ibpt.model.v1.Permission;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class UserControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static String accessToken = "Bearer ";
	private static UserVO user;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@Test
	@Order(1)
	void testCreateUser() throws JsonParseException, JsonMappingException, IOException {
		AccountCredentialsVO data = new AccountCredentialsVO("marina", "Marina Lobo", "ma@01");
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v3/user")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(data)
				.when()
					.post("new")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO createdUser = objectMapper.readValue(content, UserVO.class);
		user = createdUser;
		
		assertNotNull(createdUser);
		assertNotNull(createdUser.getKey());
		
		assertTrue(createdUser.getKey() > 0);
		
		assertEquals("marina", createdUser.getUsername());
		assertEquals("Marina Lobo", createdUser.getFullname());
		assertEquals(true, createdUser.getAccountNonExpired());
		assertEquals(true, createdUser.getAccountNonLocked());
		assertEquals(true, createdUser.getCredentialsNonExpired());
		assertEquals(true, createdUser.getEnabled());
		assertEquals(3, createdUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"_links\":{\"userVOList\":{\"href\":\"http://localhost:8888/v3/user?page=0&size=10&direction=asc&sortBy=username\"}}"));
	}
	
	@Test
	@Order(2)
	void authorization() throws JsonParseException, JsonMappingException, IOException {
		AccountCredentialsVO data = new AccountCredentialsVO("marina", "ma@01");
		
		accessToken += given()
				.basePath("/auth/signin")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.port(TestConfigs.SERVER_PORT)
				.body(data)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
						.as(TokenVO.class)
					.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v3/user")
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, accessToken)
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(3)
	void testFindById() throws JsonParseException, JsonMappingException, IOException {
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.pathParam("id", user.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO persistedUser = objectMapper.readValue(content, UserVO.class);
		
		assertNotNull(persistedUser);
		
		assertTrue(persistedUser.getKey() > 0);
		
		assertEquals("marina", persistedUser.getUsername());
		assertEquals("Marina Lobo", persistedUser.getFullname());
		assertEquals(true, persistedUser.getAccountNonExpired());
		assertEquals(true, persistedUser.getAccountNonLocked());
		assertEquals(true, persistedUser.getCredentialsNonExpired());
		assertEquals(true, persistedUser.getEnabled());
		assertEquals("COMMON_USER", persistedUser.getPermissions().get(0).getDescription());
		assertTrue(content.contains("\"_links\":{\"userVOList\":{\"href\":\"http://localhost:8888/v3/user?page=0&size=10&direction=asc&sortBy=username\"}}"));
	}
	
	@Test
	@Order(4)
	void testUpdateById() throws JsonParseException, JsonMappingException, IOException {
		UserVO userVO = new UserVO();
		userVO.setUsername("marina");
		userVO.setFullname("Marina Pires");
		userVO.setAccountNonExpired(true);
		userVO.setAccountNonLocked(true);;
		userVO.setCredentialsNonExpired(true);
		userVO.setEnabled(true);
		List<Permission> pList = new ArrayList<>();
		pList.add(new Permission(1)); // ADMIN
		userVO.setPermissions(pList);
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header("Authorization", accessToken)
				.pathParam("id", user.getKey())
				.body(userVO)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO updatedUser = objectMapper.readValue(content, UserVO.class);
		
		assertNotNull(updatedUser);
		
		assertTrue(updatedUser.getKey() > 0);
		
		assertEquals("marina", updatedUser.getUsername());
		assertEquals("Marina Pires", updatedUser.getFullname());
		assertEquals(true, updatedUser.getAccountNonExpired());
		assertEquals(true, updatedUser.getAccountNonLocked());
		assertEquals(true, updatedUser.getCredentialsNonExpired());
		assertEquals(true, updatedUser.getEnabled());
		assertEquals("ADMIN", updatedUser.getPermissions().get(0).getDescription());
		assertTrue(content.contains("\"_links\":{\"userVOList\":{\"href\":\"http://localhost:8888/v3/user?page=0&size=10&direction=asc&sortBy=username\"}}"));
	}
	
	@Test
	@Order(5)
	void testFindAll() throws JsonParseException, JsonMappingException, IOException  {
		Integer page = 0;
		Integer size = 10;
		String direction = "ASC";
		String sortBy = "fullname";
		
		var content =
				given().spec(specification)
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
				
		WrapperUserVO wrapper = objectMapper.readValue(content, WrapperUserVO.class);
		
		List<UserVO> resultList = wrapper.getEmbedded().getUsers();
		
		UserVO userOne = resultList.get(0);
		
		assertEquals("henrique", userOne.getUsername());
		assertEquals("Henrique Augusto", userOne.getFullname());
		assertEquals(true, userOne.getAccountNonExpired());
		assertEquals(true, userOne.getAccountNonLocked());
		assertEquals(true, userOne.getCredentialsNonExpired());
		assertEquals(true, userOne.getEnabled());
		assertEquals(1, userOne.getPermissions().get(0).getId());
		assertEquals("ADMIN", userOne.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(6)
	void testFindAllWithoutToken() {

		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/v3/user")
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
	@Order(7)
	void testHATEOAS() throws JsonMappingException, JsonProcessingException  {
		Integer page = 0;
		Integer size = 10;
		String direction = "asc";
		String sortBy = "fullname";
		
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
		
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/user/1\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v3/user/2\"}}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v3/user?direction=asc&sortBy=fullname&page=0&size=10&sort=fullname,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":3,\"totalPages\":1,\"number\":0}"));
	}
	
	@Test
	@Order(8)
	void testDeleteById() {
		
		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", user.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
}
