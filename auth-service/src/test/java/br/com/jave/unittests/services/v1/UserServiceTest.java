package br.com.jave.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

import br.com.jave.data.vo.v1.AccountCredentialsVO;
import br.com.jave.data.vo.v1.UserVO;
import br.com.jave.exceptions.v1.RequiredObjectIsNullException;
import br.com.jave.exceptions.v1.ResourceNotFoundException;
import br.com.jave.mappers.v1.UserMapper;
import br.com.jave.model.v1.User;
import br.com.jave.repositories.v1.UserRepository;
import br.com.jave.services.v1.UserService;
import br.com.jave.unittests.mocks.v1.PermissionMock;
import br.com.jave.unittests.mocks.v1.UserMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	UserMock input;

	@Autowired
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private UserMapper mapper;
	
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
	
	@Test
	void testFindById() {
		Integer id = 0;
		User mockEntity = input.entity();
		UserVO mockVO = input.vo();
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		UserVO persistedUser = service.findById(id);
		
		assertEquals(0, persistedUser.getKey());
		assertEquals("Username0", persistedUser.getUsername());
		assertEquals("Fullname0", persistedUser.getFullname());
		assertEquals(true, persistedUser.getAccountNonExpired());
		assertEquals(true, persistedUser.getAccountNonLocked());
		assertEquals(true, persistedUser.getCredentialsNonExpired());
		assertEquals(true, persistedUser.getEnabled());
		assertEquals("Description0", persistedUser.getPermissions().get(0).getDescription());
		assertTrue(persistedUser.getLinks().toString().contains("</v1/user?page=0&size=10&sortBy=username&direction=asc>;rel=\"userVOList\""));
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Integer id = 10;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testCreate() {
		AccountCredentialsVO data = new AccountCredentialsVO("username0", "fullname0", "password0");
		User mockEntity = input.entity();
		UserVO mockVO = input.vo();
		
		when(repository.save(any(User.class))).thenReturn(mockEntity);
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		UserVO createdUser = service.create(data);
		
		assertEquals(0, createdUser.getKey());
		assertEquals("Username0", createdUser.getUsername());
		assertEquals("Fullname0", createdUser.getFullname());
		assertEquals(true, createdUser.getAccountNonExpired());
		assertEquals(true, createdUser.getAccountNonLocked());
		assertEquals(true, createdUser.getCredentialsNonExpired());
		assertEquals(true, createdUser.getEnabled());
		assertEquals("Description0", createdUser.getPermissions().get(0).getDescription());
		assertTrue(createdUser.getLinks().toString().contains("</v1/user?page=0&size=10&sortBy=username&direction=asc>;rel=\"userVOList\""));
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById() {
		Integer id = 0;
		User mockEntity = input.entity();
		User persistedEntity = mockEntity;
		UserVO mockVO = input.vo();
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		UserVO updatedUser = service.updateById(id, mockVO);
		
		assertEquals(0, updatedUser.getKey());
		assertEquals("Username0", updatedUser.getUsername());
		assertEquals("Fullname0", updatedUser.getFullname());
		assertEquals(true, updatedUser.getAccountNonExpired());
		assertEquals(true, updatedUser.getAccountNonLocked());
		assertEquals(true, updatedUser.getCredentialsNonExpired());
		assertEquals(true, updatedUser.getEnabled());
		assertEquals("Description0", updatedUser.getPermissions().get(0).getDescription());
		assertTrue(updatedUser.getLinks().toString().contains("</v1/user?page=0&size=10&sortBy=username&direction=asc>;rel=\"userVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		UserVO mockVO = input.vo();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateByIdWithRequiredObjectIsNullException() {
		Integer id = 1;
		UserVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void deleteById() {
		Integer id = 0;
		User mockEntity = input.entity();
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		
		service.deleteById(id);
	}
	
	@Test
	void testDeleteByIdWithResourceNotFoundException() {
		Integer id = 10;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
}
