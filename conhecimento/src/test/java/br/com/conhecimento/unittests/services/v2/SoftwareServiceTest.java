package br.com.conhecimento.unittests.services.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.util.Date;
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

import br.com.conhecimento.data.vo.v2.SoftwareVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v2.SoftwareMapper;
import br.com.conhecimento.model.v2.Software;
import br.com.conhecimento.repositories.v2.SoftwareRepository;
import br.com.conhecimento.services.v2.SoftwareService;
import br.com.conhecimento.unittests.mocks.v2.SoftwareMock;
import br.com.conhecimento.unittests.mocks.v2.UserAuditMock;

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
		
		assertEquals("Username0", persistedSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(persistedSoftware.getLastUpdateDatetime()));
		assertEquals("Username0", persistedSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(persistedSoftware.getCreationDatetime()));
		
		assertTrue(persistedSoftware.getLinks().toString().contains("</v2/software?page=0&size=10&sortBy=name&direction=asc>;rel=\"softwareVOList\""));
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
		
		SoftwareVO createdSoftware = service.create(mockVO, UserAuditMock.entity());
		
		assertNotNull(createdSoftware);
		
		assertEquals(0, createdSoftware.getKey());
		assertEquals("Name0", createdSoftware.getName());
		
		assertEquals("Username0", createdSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(createdSoftware.getLastUpdateDatetime()));
		assertEquals("Username0", createdSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(createdSoftware.getCreationDatetime()));
		
		assertTrue(createdSoftware.getLinks().toString().contains("</v2/software?page=0&size=10&sortBy=name&direction=asc>;rel=\"softwareVOList\""));
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null, UserAuditMock.entity());
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
		
		SoftwareVO updatedSoftware = service.updateById(id, mockVO, UserAuditMock.entity());
		
		assertNotNull(updatedSoftware);
		
		assertEquals(1, updatedSoftware.getKey());
		assertEquals("Name1", updatedSoftware.getName());
		
		assertEquals("Username0", updatedSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(updatedSoftware.getLastUpdateDatetime()));
		assertEquals("Username0", updatedSoftware.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(updatedSoftware.getCreationDatetime()));
		
		assertTrue(updatedSoftware.getLinks().toString().contains("</v2/software?page=0&size=10&sortBy=name&direction=asc>;rel=\"softwareVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		SoftwareVO mockVO = input.vo();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, mockVO, UserAuditMock.entity());
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
			service.updateById(id, mockVO, UserAuditMock.entity());
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
