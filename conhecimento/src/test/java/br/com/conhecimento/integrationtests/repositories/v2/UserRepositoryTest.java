package br.com.conhecimento.integrationtests.repositories.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.model.v2.User;
import br.com.conhecimento.repositories.v2.UserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class UserRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private UserRepository repository;
	
	@Test
	@Order(1)
	void testFindByUsername() {
		
		String username = "henrique";
		
		User persistedUser = repository.findByUsername(username);
		
		assertNotNull(persistedUser);
		assertEquals(1, persistedUser.getId());
		assertEquals("henrique", persistedUser.getUsername());
		assertEquals("Henrique Augusto", persistedUser.getFullname());
		assertEquals(true, persistedUser.getAccountNonExpired());
		assertEquals(true, persistedUser.getAccountNonLocked());
		assertEquals(true, persistedUser.getCredentialsNonExpired());
		assertEquals(true, persistedUser.getEnabled());
		assertEquals("ADMIN", persistedUser.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(2)
	void testFindCustomPageable() {
		
		String username = "";
		String permissionDescription = "";
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "username"));
		
		Page<User> pageResult = repository
				.findPageableByUsernameContainingAndPermissionsDescriptionContaining(username, permissionDescription, pageable);
		
		List<User> resultList = pageResult.getContent();
		
		User userOne = resultList.get(0);
		
		assertNotNull(userOne);
		assertEquals(1, userOne.getId());
		assertEquals("henrique", userOne.getUsername());
		assertEquals("Henrique Augusto", userOne.getFullname());
		assertEquals(true, userOne.getAccountNonExpired());
		assertEquals(true, userOne.getAccountNonLocked());
		assertEquals(true, userOne.getCredentialsNonExpired());
		assertEquals(true, userOne.getEnabled());
		assertEquals("ADMIN", userOne.getPermissions().get(0).getDescription());
		
		User userTwo = resultList.get(1);
		
		assertNotNull(userTwo);
		assertEquals(2, userTwo.getId());
		assertEquals("ricardo", userTwo.getUsername());
		assertEquals("Ricardo Augusto", userTwo.getFullname());
		assertEquals(true, userTwo.getAccountNonExpired());
		assertEquals(true, userTwo.getAccountNonLocked());
		assertEquals(true, userTwo.getCredentialsNonExpired());
		assertEquals(true, userTwo.getEnabled());
		assertEquals("ADMIN", userTwo.getPermissions().get(0).getDescription());
	}
	
}
