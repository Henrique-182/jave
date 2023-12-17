package br.com.conhecimento.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.conhecimento.data.vo.v1.SoftwareVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v1.SoftwareMapper;
import br.com.conhecimento.model.v1.Software;
import br.com.conhecimento.repositories.v1.SoftwareRepository;
import br.com.conhecimento.services.v1.SoftwareService;
import br.com.conhecimento.unittests.mocks.v1.SoftwareMock;

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
	}
	
	@Test
	void testFindAll() {
		
		List<Software> mockEntityList = input.entityList();
		List<SoftwareVO> mockVOList = input.voList();
				
		when(repository.findAll()).thenReturn(mockEntityList);
		when(mapper.toVOList(mockEntityList)).thenReturn(mockVOList);
		
		List<SoftwareVO> resultList = service.findAll();
		
		SoftwareVO softwareOne = resultList.get(1);
		
		assertNotNull(softwareOne);
		
		assertEquals(1, softwareOne.getKey());
		assertEquals("Name1", softwareOne.getName());
		
		SoftwareVO softwareTwo = resultList.get(2);
		
		assertNotNull(softwareTwo);
		
		assertEquals(2, softwareTwo.getKey());
		assertEquals("Name2", softwareTwo.getName());
	}
	
	@Test
	void testFindById() {
		
		Integer id = 1;
		
		Software mockEntity = input.entity(id);
		SoftwareVO mockVO = input.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		SoftwareVO persistedSoftware = service.findById(id);
		
		assertNotNull(persistedSoftware);
		
		assertEquals(1, persistedSoftware.getKey());
		assertEquals("Name1", persistedSoftware.getName());
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
		
		Software mockEntity = input.entity();
		Software persistedEntity = mockEntity;
		SoftwareVO mockVO = input.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		SoftwareVO createdSoftware = service.create(mockVO);
		
		assertNotNull(createdSoftware);
		
		assertEquals(0, createdSoftware.getKey());
		assertEquals("Name0", createdSoftware.getName());
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
	void testUpdateById() {
		
		Integer id = 1;
		
		Software mockEntity = input.entity(id);
		Software persistedEntity = mockEntity;
		SoftwareVO mockVO = input.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		SoftwareVO updatedSoftware = service.updateById(id, mockVO);
		
		assertNotNull(updatedSoftware);
		
		assertEquals(1, updatedSoftware.getKey());
		assertEquals("Name1", updatedSoftware.getName());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		SoftwareVO mockVO = input.vo();
		
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
		SoftwareVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		
		Integer id = 1;
		
		Software mockEntity = input.entity(id);
		
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
