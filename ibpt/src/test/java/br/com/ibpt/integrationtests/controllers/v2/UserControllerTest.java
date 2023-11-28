package br.com.ibpt.integrationtests.controllers.v2;

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

import br.com.ibpt.config.TestConfigs;
import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.integrationtests.vo.v1.AccountCredentialsVO;
import br.com.ibpt.integrationtests.vo.v1.TokenVO;
import br.com.ibpt.integrationtests.vo.v1.UserVO;
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
	public void testCreateUser() throws JsonParseException, JsonMappingException, IOException {
		AccountCredentialsVO data = new AccountCredentialsVO("marina", "Marina Lobo", "ma@01");
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v2/usuario")
				.setPort(TestConfigs.SERVER_PORT)
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(data)
				.when()
					.post("novo")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
				
		UserVO createdUser = objectMapper.readValue(content, UserVO.class);
		user = createdUser;
		
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
	}
	
	@Test
	@Order(2)
	public void authorization() throws JsonParseException, JsonMappingException, IOException {
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
				.setBasePath("/v2/usuario")
				.addHeader("Authorization", accessToken)
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
				.pathParam("id", user.getId())
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
	@Order(4)
	void testUpdateById() throws JsonParseException, JsonMappingException, IOException {
		UserVO userVO = new UserVO();
		userVO.setUserName("marina");
		userVO.setFullName("Marina Pires");
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
				.pathParam("id", user.getId())
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
		
		assertEquals("marina", result.getUserName());
		assertEquals("Marina Pires", result.getFullName());
		assertEquals(true, result.getAccountNonExpired());
		assertEquals(true, result.getAccountNonLocked());
		assertEquals(true, result.getCredentialsNonExpired());
		assertEquals(true, result.getEnabled());
		assertEquals("ADMIN", result.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(5)
	void testFindAll() throws JsonParseException, JsonMappingException, IOException  {
		Integer page = 0;
		Integer size = 10;
		String direction = "ASC";
		String sortBy = "nomeCompleto";
		
		var content =
				given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.queryParam("pagina", page)
				.queryParam("tamanho", size)
				.queryParam("direcao", direction)
				.queryParam("ordenadoPor", sortBy)
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
		
		assertEquals("henrique", userOne.getUserName());
		assertEquals("Henrique Augusto", userOne.getFullName());
		assertEquals(true, userOne.getAccountNonExpired());
		assertEquals(true, userOne.getAccountNonLocked());
		assertEquals(true, userOne.getCredentialsNonExpired());
		assertEquals(true, userOne.getEnabled());
		assertEquals(1, userOne.getPermissions().get(0).getId());
		assertEquals("ADMIN", userOne.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(6)
	void testDeleteById() {
		
		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_JSON)
			.pathParam("id", user.getId())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
}
