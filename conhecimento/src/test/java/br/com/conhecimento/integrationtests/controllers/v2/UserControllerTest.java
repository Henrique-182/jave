package br.com.conhecimento.integrationtests.controllers.v2;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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

import br.com.conhecimento.configs.v1.TestConfig;
import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.integrationtests.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.integrationtests.vo.v2.TokenVO;
import br.com.conhecimento.integrationtests.vo.v2.UserVO;
import br.com.conhecimento.integrationtests.vo.wrappers.v2.UserWrapperVO;
import br.com.conhecimento.model.v2.Permission;
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
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static UserVO user;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		credentials = new AccountCredentialsVO("marina", "Marina Lobo", "ma@01");
	}
	
	@Test
	@Order(0)
	void testCreateUserWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		var content = given()
				.basePath("/v2/user/new")
				.port(TestConfig.SERVER_PORT)
				.contentType(TestConfig.CONTENT_TYPE_JSON)
				.body(credentials)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		UserVO createdUser = mapper.readValue(content, UserVO.class);
		user = createdUser;
		
		assertEquals(3, createdUser.getKey());
		assertEquals("marina", createdUser.getUsername());
		assertEquals("Marina Lobo", createdUser.getFullname());
		assertEquals(true, createdUser.getAccountNonExpired());
		assertEquals(true, createdUser.getAccountNonLocked());
		assertEquals(true, createdUser.getCredentialsNonExpired());
		assertEquals(true, createdUser.getEnabled());
		assertEquals(3, createdUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"userVOList\":{\"href\":\"http://localhost:8888/v2/user?page=0&size=10&sortBy=username&direction=asc\"}"));
	}
	
	@Test
	@Order(1)
	void testAuthorization() {
		
		var tokenVO = given()
			 .basePath("/auth/signin")
			 .port(TestConfig.SERVER_PORT)
			 .contentType(TestConfig.CONTENT_TYPE_JSON)
			 .body(credentials)
			 .when()
		 		 .post()
			 .then()
				 .statusCode(200)
			 .extract()
				 .body()
				 .as(TokenVO.class);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v2/user")
				.setPort(TestConfig.SERVER_PORT)
				.addHeader(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.setContentType(TestConfig.CONTENT_TYPE_JSON)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				 .pathParam("id", user.getKey())
				 .when()
			 		 .get("{id}")
				 .then()
					 .statusCode(200)
				 .extract()
					 .body()
					 .asString();
		
		UserVO persistedUser = mapper.readValue(content, UserVO.class);
		
		assertEquals(3, persistedUser.getKey());
		assertEquals("marina", persistedUser.getUsername());
		assertEquals("Marina Lobo", persistedUser.getFullname());
		assertEquals(true, persistedUser.getAccountNonExpired());
		assertEquals(true, persistedUser.getAccountNonLocked());
		assertEquals(true, persistedUser.getCredentialsNonExpired());
		assertEquals(true, persistedUser.getEnabled());
		assertEquals(3, persistedUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"userVOList\":{\"href\":\"http://localhost:8888/v2/user?page=0&size=10&sortBy=username&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		
		List<Permission> permissionList = new ArrayList<>();
		permissionList.add(new Permission(2));
		user.setPermissions(permissionList);
		
		var content = given().spec(specification)
				 .pathParam("id", user.getKey())
				 .body(user)
				 .when()
			 		 .put("{id}")
				 .then()
					 .statusCode(200)
				 .extract()
					 .body()
					 .asString();
		
		UserVO updatedUser = mapper.readValue(content, UserVO.class);
		user = updatedUser;
		
		assertEquals(3, updatedUser.getKey());
		assertEquals("marina", updatedUser.getUsername());
		assertEquals("Marina Lobo", updatedUser.getFullname());
		assertEquals(true, updatedUser.getAccountNonExpired());
		assertEquals(true, updatedUser.getAccountNonLocked());
		assertEquals(true, updatedUser.getCredentialsNonExpired());
		assertEquals(true, updatedUser.getEnabled());
		assertEquals(2, updatedUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"userVOList\":{\"href\":\"http://localhost:8888/v2/user?page=0&size=10&sortBy=username&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testFindPageable() throws JsonMappingException, JsonProcessingException {
		
		String permissionDescription = "ADMIN";
		
		var content = given().spec(specification)
				.queryParam("permissionDescription", permissionDescription)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		UserWrapperVO wrapper = mapper.readValue(content, UserWrapperVO.class);
		
		List<UserVO> resultList = wrapper.getEmbedded().getUsers();
		
		UserVO userOne = resultList.get(0);
		
		assertEquals(1, userOne.getKey());
		assertEquals("henrique", userOne.getUsername());
		assertEquals("Henrique Augusto", userOne.getFullname());
		assertEquals(true, userOne.getAccountNonExpired());
		assertEquals(true, userOne.getAccountNonLocked());
		assertEquals(true, userOne.getCredentialsNonExpired());
		assertEquals(true, userOne.getEnabled());
		assertEquals(1, userOne.getPermissions().get(0).getId());
		
		UserVO userTwo = resultList.get(1);
		
		assertEquals(2, userTwo.getKey());
		assertEquals("ricardo", userTwo.getUsername());
		assertEquals("Ricardo Augusto", userTwo.getFullname());
		assertEquals(true, userTwo.getAccountNonExpired());
		assertEquals(true, userTwo.getAccountNonLocked());
		assertEquals(true, userTwo.getCredentialsNonExpired());
		assertEquals(true, userTwo.getEnabled());
		assertEquals(1, userTwo.getPermissions().get(0).getId());
	}
	
	@Test
	@Order(5)
	void testHATEOAS() {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/user/1\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/user/2\"}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v2/user?page=0&size=10&sort=username,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":3,\"totalPages\":1,\"number\":0}"));
		
	}
	
	@Test
	@Order(6)
	void testDeleteById() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
			 .pathParam("id", user.getKey())
			 .when()
		 		 .delete("{id}")
			 .then()
				 .statusCode(204);
		
	}
	
}
