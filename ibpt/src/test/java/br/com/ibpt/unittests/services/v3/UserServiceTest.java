package br.com.ibpt.unittests.services.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ibpt.data.vo.v3.AccountCredentialsVO;
import br.com.ibpt.data.vo.v3.UserVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v3.UserMapper;
import br.com.ibpt.model.v3.User;
import br.com.ibpt.repositories.v3.UserRepository;
import br.com.ibpt.services.v3.UserService;
import br.com.ibpt.unittests.mocks.v3.UserMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private UserMock input;
	
	@Autowired
	@InjectMocks
	private UserService service;

	@Mock
	private UserRepository repository;
	
	@Spy
	private UserMapper mapper;
	
	@BeforeEach
	void setUp() {
		input = new UserMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testCreate() {
		User mockEntity = input.mockEntity(1);
		UserVO mockVO = input.mockVO(1);
		AccountCredentialsVO user = new AccountCredentialsVO("Username1", "Fullname1", "Password1");
		
		when(repository.save(any(User.class))).thenReturn(mockEntity);
		when(mapper.toVO(any(User.class))).thenReturn(mockVO);
		
		UserVO createdUser = service.create(user);
		
		assertNotNull(createdUser);
		
		assertEquals(1, createdUser.getKey());
		assertEquals("Username1", createdUser.getUsername());
		assertEquals("Fullname1", createdUser.getFullname());
		assertEquals(false, createdUser.getAccountNonExpired());
		assertEquals(false, createdUser.getAccountNonLocked());
		assertEquals(false, createdUser.getCredentialsNonExpired());
		assertEquals(false, createdUser.getEnabled());
		
		assertEquals("Description1", createdUser.getPermissions().get(0).getDescription());
		
		assertEquals("</v3/user?page=0&size=10&direction=asc&sortBy=username>;rel=\"userVOList\"", createdUser.getLinks().toString());
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testFindById() {
		Integer id = 1;
		User mockEntity = input.mockEntity(id);
		UserVO mockVO = input.mockVO(id);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(any(User.class))).thenReturn(mockVO);
		
		UserVO persistedUser = service.findById(id);
		
		assertNotNull(persistedUser);
		
		assertEquals(1, persistedUser.getKey());
		assertEquals("Username1", persistedUser.getUsername());
		assertEquals("Fullname1", persistedUser.getFullname());
		assertEquals(false, persistedUser.getAccountNonExpired());
		assertEquals(false, persistedUser.getAccountNonLocked());
		assertEquals(false, persistedUser.getCredentialsNonExpired());
		assertEquals(false, persistedUser.getEnabled());
		
		assertEquals("Description1", persistedUser.getPermissions().get(0).getDescription());
		
		assertEquals("</v3/user?page=0&size=10&direction=asc&sortBy=username>;rel=\"userVOList\"", persistedUser.getLinks().toString());
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Integer id = 1000;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		UserVO mockVO = input.mockVO(id);
		mockVO.setUsername(id + "Username");
		mockVO.setFullname(id + "Fullname");
		User mockEntity = input.mockEntity(id);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(repository.save(any(User.class))).thenReturn(mockEntity);
		when(mapper.toVO(any(User.class))).thenReturn(mockVO);
		
		UserVO updatedUser = service.updateById(id, mockVO);
		
		assertNotNull(updatedUser);
		
		assertEquals(2, updatedUser.getKey());
		assertEquals("2Username", updatedUser.getUsername());
		assertEquals("2Fullname", updatedUser.getFullname());
		assertEquals(true, updatedUser.getAccountNonExpired());
		assertEquals(true, updatedUser.getAccountNonLocked());
		assertEquals(true, updatedUser.getCredentialsNonExpired());
		assertEquals(true, updatedUser.getEnabled());
		
		assertEquals("Description2", updatedUser.getPermissions().get(0).getDescription());
		
		assertEquals("</v3/user?page=0&size=10&direction=asc&sortBy=username>;rel=\"userVOList\"", updatedUser.getLinks().toString());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		UserVO mockVO = input.mockVO();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateByIdWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(2, null);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		User entity = input.mockEntity(1); 
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		service.deleteById(1);
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
