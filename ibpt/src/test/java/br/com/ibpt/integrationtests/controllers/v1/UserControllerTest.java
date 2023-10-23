package br.com.ibpt.integrationtests.controllers.v1;

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
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonParseException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonMappingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v1.UserVO;
import br.com.ibpt.model.v1.Permission;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTest {

	private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
	
	private static String accessToken = "Bearer ";
	private static Integer idUser;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@Test
	@Order(1)
	void testCreateUser() throws JsonParseException, JsonMappingException, IOException {
		AccountCredentialsVO user = new AccountCredentialsVO("marina", "Marina Lobo", "ma@01");
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/usuario/novo")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO createdUser = objectMapper.readValue(content, UserVO.class);
		
		assertNotNull(createdUser);
		assertNotNull(createdUser.getId());
		
		assertTrue(createdUser.getId() > 0);
		
		assertEquals("marina", createdUser.getUserName());
		assertEquals("Marina Lobo", createdUser.getFullName());
		assertEquals(true, createdUser.getAccountNonExpired());
		assertEquals(true, createdUser.getAccountNonLocked());
		assertEquals(true, createdUser.getCredentialsNonExpired());
		assertEquals(true, createdUser.getEnabled());
		assertEquals(3, createdUser.getPermissions().get(0).getId());
		
		idUser = createdUser.getId();
	}
	@Test
	@Order(2)
	void authorization() throws JsonParseException, JsonMappingException, IOException {
		AccountCredentialsVO user = new AccountCredentialsVO("marina", "ma@01");
		
		specification = null;
		
		accessToken = accessToken +
				given()
				.basePath("/auth/signin")
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.port(TestConfigs.SERVER_PORT)
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
	@Order(3)
	void testFindAll() throws JsonParseException, JsonMappingException, IOException {
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/usuario")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header("Authorization", accessToken)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		var result = objectMapper.readValue(content, ArrayList.class).toString();
		
		assertNotNull(result);
		
		assertTrue(result.contains("userName=marina"));
	}
	
	@Test
	@Order(4)
	void testFindById() throws JsonParseException, JsonMappingException, IOException {
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/usuario")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header("Authorization", accessToken)
				.pathParam("id", idUser)
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO result = objectMapper.readValue(content, UserVO.class);
		
		assertNotNull(result);
		
		assertTrue(result.getId() > 0);
		
		assertEquals("marina", result.getUserName());
		assertEquals("Marina Lobo", result.getFullName());
		assertEquals(true, result.getAccountNonExpired());
		assertEquals(true, result.getAccountNonLocked());
		assertEquals(true, result.getCredentialsNonExpired());
		assertEquals(true, result.getEnabled());
		assertEquals("COMMON_USER", result.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(5)
	void testUpdateById() throws JsonParseException, JsonMappingException, IOException {
		UserVO userVO = new UserVO();
		userVO.setUserName("marina2");
		userVO.setFullName("Marina Lobo2");
		userVO.setAccountNonExpired(true);
		userVO.setAccountNonLocked(true);;
		userVO.setCredentialsNonExpired(true);
		userVO.setEnabled(true);
		List<Permission> pList = new ArrayList<>();
		pList.add(new Permission(1)); // ADMIN
		userVO.setPermissions(pList);
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/usuario")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.header("Authorization", accessToken)
				.pathParam("id", idUser)
				.body(userVO)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO result = objectMapper.readValue(content, UserVO.class);
		
		assertNotNull(result);
		
		assertTrue(result.getId() > 0);
		
		assertEquals("marina2", result.getUserName());
		assertEquals("Marina Lobo2", result.getFullName());
		assertEquals(true, result.getAccountNonExpired());
		assertEquals(true, result.getAccountNonLocked());
		assertEquals(true, result.getCredentialsNonExpired());
		assertEquals(true, result.getEnabled());
		assertEquals("ADMIN", result.getPermissions().get(0).getDescription());
	}
}
