package br.com.ibpt.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;
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

import br.com.ibpt.data.vo.v1.AccountCredentialsVO;
import br.com.ibpt.data.vo.v1.UserVO;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.UserMapper;
import br.com.ibpt.model.v1.User;
import br.com.ibpt.repositories.v1.UserRepository;
import br.com.ibpt.services.v1.UserService;
import br.com.ibpt.unittests.mocks.v1.UserMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private UserMock input;
	
	@Autowired
	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;
	
	@Spy
	private UserMapper userMapper;
	
	@BeforeEach
	void setUp() {
		input = new UserMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindById() {
		Integer id = 1;
		User mockEntity = input.mockEntity(id);
		UserVO mockVO = input.mockVO(id);
		
		when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(userMapper.toUserVO(any(User.class))).thenReturn(mockVO);
		
		UserVO result = userService.findById(id);
		
		assertNotNull(result);
		
		assertEquals(1, result.getId());
		assertEquals("Username1", result.getUserName());
		assertEquals("Full Name1", result.getFullName());
		assertEquals(false, result.getAccountNonExpired());
		assertEquals(false, result.getAccountNonLocked());
		assertEquals(false, result.getCredentialsNonExpired());
		assertEquals(false, result.getEnabled());
		assertEquals("Description1", result.getPermissions().get(0).getDescription());
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Integer id = 1000;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			userService.findById(id);
		});
		
		String expectedMessage = "No records found for this id!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testFindAll() {
		List<User> mockEntityList = input.mockEntityList();
		List<UserVO> mockVOList = input.mockVOList();
		
		when(userRepository.findAll()).thenReturn(mockEntityList);
		when(userMapper.toUserVOList(anyList())).thenReturn(mockVOList);
		
		List<UserVO> resultList = userService.findAll();
		
		assertNotNull(resultList);
		
		UserVO resultTwo = resultList.get(2);
		assertNotNull(resultTwo);
		assertEquals(2, resultTwo.getId());
		assertEquals("Username2", resultTwo.getUserName());
		assertEquals("Full Name2", resultTwo.getFullName());
		assertEquals(true, resultTwo.getAccountNonExpired());
		assertEquals(true, resultTwo.getAccountNonLocked());
		assertEquals(true, resultTwo.getCredentialsNonExpired());
		assertEquals(true, resultTwo.getEnabled());
		assertEquals("Description2", resultTwo.getPermissions().get(0).getDescription());
	
	}
	
	@Test
	void testCreate() {
		User mockEntity = input.mockEntity(1);
		UserVO mockVO = input.mockVO(1);
		AccountCredentialsVO user = new AccountCredentialsVO("Username0", "Full Name0", "Password0");
		
		when(userRepository.save(any(User.class))).thenReturn(mockEntity);
		when(userMapper.toUserVO(any(User.class))).thenReturn(mockVO);
		
		UserVO result = userService.create(user);
		
		assertNotNull(result);
		
		assertEquals(1, result.getId());
		assertEquals("Username1", result.getUserName());
		assertEquals("Full Name1", result.getFullName());
		assertEquals(false, result.getAccountNonExpired());
		assertEquals(false, result.getAccountNonLocked());
		assertEquals(false, result.getCredentialsNonExpired());
		assertEquals(false, result.getEnabled());
		assertEquals("Description1", result.getPermissions().get(0).getDescription());
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		UserVO mockVO = input.mockVO(id);
		mockVO.setUserName(id + "Username");
		mockVO.setFullName(id + "Full Name");
		User mockEntity = input.mockEntity(id);
		
		when(userRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(userRepository.save(any(User.class))).thenReturn(mockEntity);
		when(userMapper.toUserVO(any(User.class))).thenReturn(mockVO);
		
		UserVO result = userService.updateById(id, mockVO);
		
		assertNotNull(result);
		
		assertEquals(2, result.getId());
		assertEquals("2Username", result.getUserName());
		assertEquals("2Full Name", result.getFullName());
		assertEquals(true, result.getAccountNonExpired());
		assertEquals(true, result.getAccountNonLocked());
		assertEquals(true, result.getCredentialsNonExpired());
		assertEquals(true, result.getEnabled());
		assertEquals("Description2", result.getPermissions().get(0).getDescription());
	}
}
