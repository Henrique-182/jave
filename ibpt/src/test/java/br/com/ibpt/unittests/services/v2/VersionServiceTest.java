package br.com.ibpt.unittests.services.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
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

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.VersionMapper;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.repositories.v1.VersionRepository;
import br.com.ibpt.repositories.v2.VersionCustomRepository;
import br.com.ibpt.services.v2.VersionService;
import br.com.ibpt.unittests.mocks.v1.VersionMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VersionServiceTest {

	VersionMock input;
	
	@Autowired
	@InjectMocks
	VersionService service;
	
	@Mock
	VersionRepository repository;
	
	@Mock
	VersionCustomRepository customRepository;
	
	@Spy
	VersionMapper mapper;
	
	@BeforeEach
	void setup() {
		input = new VersionMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testCreate() {
		Version mockEntity = input.mockEntity();
		Version persisted = mockEntity;
		VersionVO mockVO = input.mockVO();
		
		when(mapper.toVersion(any(VersionVO.class))).thenReturn(mockEntity);
		when(repository.save(any(Version.class))).thenReturn(persisted);
		when(mapper.toVersionVO(any(Version.class))).thenReturn(mockVO);
		
		VersionVO createdVersion = service.create(mockVO);
		
		assertNotNull(createdVersion);
		assertEquals(0, createdVersion.getKey());
		assertEquals("Name0", createdVersion.getName());
		assertEquals(new Date(0), createdVersion.getEffectivePeriodUntil());
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
		
		Version mockEntity = input.mockEntity(id);
		VersionVO mockVO = input.mockVO(id);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(mapper.toVersionVO(any(Version.class))).thenReturn(mockVO);
		
		VersionVO persistedVersion = service.findById(id);
		
		assertNotNull(persistedVersion);
		assertEquals(2, persistedVersion.getKey());
		assertEquals("Name2", persistedVersion.getName());
		assertEquals(new Date(2), persistedVersion.getEffectivePeriodUntil());
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		
		VersionVO mockVO = input.mockVO(id);
		mockVO.setName(id + "Name");
		Date date = new Date();
		mockVO.setEffectivePeriodUntil(date);
		
		Version mockEntity = input.mockEntity();
		Version persisted = mockEntity;
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(repository.save(any(Version.class))).thenReturn(persisted);
		when(mapper.toVersionVO(any(Version.class))).thenReturn(mockVO);
		
		VersionVO updatedVersion = service.updateById(id, mockVO);
		
		assertNotNull(updatedVersion);
		assertEquals(2, updatedVersion.getKey());
		assertEquals("2Name", updatedVersion.getName());
		assertEquals(date, updatedVersion.getEffectivePeriodUntil());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		VersionVO mockVO = input.mockVO();
		
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
		Version entity = input.mockEntity(1); 
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		service.deleteById(1);
	}
	
}
