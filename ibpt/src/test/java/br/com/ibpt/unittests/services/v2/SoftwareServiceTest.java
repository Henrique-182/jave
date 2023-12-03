package br.com.ibpt.unittests.services.v2;

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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ibpt.data.vo.v2.SoftwareVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v2.SoftwareMapper;
import br.com.ibpt.model.v2.Software;
import br.com.ibpt.repositories.v2.SoftwareRepository;
import br.com.ibpt.services.v2.SoftwareService;
import br.com.ibpt.unittests.mocks.v2.SoftwareMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class SoftwareServiceTest {

	SoftwareMock input;
	
	@Autowired
	@InjectMocks
	SoftwareService service;
	
	@Mock
	SoftwareRepository repository;
	
	@Mock
	SoftwareMapper mapper;
	
	@BeforeEach
	void setup() {
		input = new SoftwareMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testCreate() {
		Software mockEntity = input.mockEntity(2);
		Software persisted = mockEntity;
		SoftwareVO mockVO = input.mockVO(2);
		
		when(mapper.toEntity(any(SoftwareVO.class))).thenReturn(mockEntity);
		when(repository.save(any(Software.class))).thenReturn(persisted);
		when(mapper.toVO(any(Software.class))).thenReturn(mockVO);
		
		SoftwareVO createdSoftware = service.create(mockVO);
		
		assertNotNull(createdSoftware);
		assertEquals(2, createdSoftware.getKey());
		assertEquals("Name2", createdSoftware.getName());
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
		Integer id = 2;
		
		Software mockEntity = input.mockEntity(id);
		SoftwareVO mockVO = input.mockVO(id);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(any(Software.class))).thenReturn(mockVO);
		
		SoftwareVO persistedSoftware = service.findById(id);
		
		assertNotNull(persistedSoftware);
		assertEquals(2, persistedSoftware.getKey());
		assertEquals("Name2", persistedSoftware.getName());
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		
		SoftwareVO mockVO = input.mockVO(id);
		mockVO.setName(id + "Name");
		
		Software mockEntity = input.mockEntity();
		Software persisted = mockEntity;
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(repository.save(any(Software.class))).thenReturn(persisted);
		when(mapper.toVO(any(Software.class))).thenReturn(mockVO);
		
		SoftwareVO updatedSoftware = service.updateById(id, mockVO);
		
		assertNotNull(updatedSoftware);
		assertEquals(2, updatedSoftware.getKey());
		assertEquals("2Name", updatedSoftware.getName());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		SoftwareVO mockVO = input.mockVO();
		
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
		Software entity = input.mockEntity(1); 
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		service.deleteById(1);
	}
}
