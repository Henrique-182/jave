package br.com.conhecimento.unittests.services.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.conhecimento.model.v2.User;
import br.com.conhecimento.repositories.v2.UserRepository;
import br.com.conhecimento.services.v2.UserService;
import br.com.conhecimento.unittests.mocks.v2.PermissionMock;
import br.com.conhecimento.unittests.mocks.v2.UserMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	UserMock input;

	@Autowired
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@BeforeEach
	void setup() {
		input = new UserMock();
	}
	
	@Test
	void testLoadByUsername() {
		
		String username = "username0";
		User mockEntity = input.entity();
		
		when(repository.findByUsername(username)).thenReturn(mockEntity);
		
		UserDetails persistedEntity = service.loadUserByUsername(username);
		
		assertNotNull(persistedEntity);
		
		assertEquals("Username0", persistedEntity.getUsername());
		assertEquals(true, persistedEntity.isAccountNonExpired());
		assertEquals(true, persistedEntity.isAccountNonLocked());
		assertEquals(true, persistedEntity.isCredentialsNonExpired());
		assertEquals(true, persistedEntity.isEnabled());
		assertTrue(persistedEntity.getAuthorities().contains(PermissionMock.entity(0)));
	}
	
}
