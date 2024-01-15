package br.com.conhecimento.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
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

import br.com.conhecimento.data.vo.v1.KnowledgeVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v1.KnowledgeMapper;
import br.com.conhecimento.model.v1.Knowledge;
import br.com.conhecimento.repositories.v1.KnowledgeRepository;
import br.com.conhecimento.services.v1.KnowledgeService;
import br.com.conhecimento.unittests.mocks.v1.KnowledgeMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class KnowledgeServiceTest {
	
	KnowledgeMock input;
	
	@Autowired
	@InjectMocks
	KnowledgeService service;
	
	@Mock
	KnowledgeRepository repository;
	
	@Mock
	KnowledgeMapper mapper;
	
	@BeforeEach
	void setup() {
		input = new KnowledgeMock();
	}
	
	@Test
	void testFindById() {
		
		Integer id = 1;
		
		Knowledge mockEntity = input.entity(id);
		KnowledgeVO mockVO = input.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		KnowledgeVO persistedKnowledge = service.findById(id);
		
		assertNotNull(persistedKnowledge);
		
		assertEquals(1, persistedKnowledge.getKey());
		assertEquals("Title1", persistedKnowledge.getTitle());
		assertEquals("Description1", persistedKnowledge.getDescription());
		assertEquals("Content1", persistedKnowledge.getContent());
		
		assertEquals("Stac", persistedKnowledge.getSoftware().getName());
		
		assertEquals("Name0", persistedKnowledge.getTopics().get(0).getName());
		assertEquals("Name1", persistedKnowledge.getTopics().get(1).getName());
		
		assertTrue(persistedKnowledge.getLinks().toString().contains("</v1/knowledge?page=0&size=10&sortBy=title&direction=asc>;rel=\"knowledgeVOList\""));
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
	void testCreate() throws ParseException {
		
		Knowledge mockEntity = input.entity();
		Knowledge persistedEntity = mockEntity;
		KnowledgeVO mockVO = input.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		KnowledgeVO createdKnowledge = service.create(mockVO);
		
		assertNotNull(createdKnowledge);
		
		assertEquals(0, createdKnowledge.getKey());
		assertEquals("Title0", createdKnowledge.getTitle());
		assertEquals("Description0", createdKnowledge.getDescription());
		assertEquals("Content0", createdKnowledge.getContent());
		
		assertEquals("Esti", createdKnowledge.getSoftware().getName());
		
		assertEquals("Name0", createdKnowledge.getTopics().get(0).getName());
		assertEquals("Name1", createdKnowledge.getTopics().get(1).getName());
		
		assertTrue(createdKnowledge.getLinks().toString().contains("</v1/knowledge?page=0&size=10&sortBy=title&direction=asc>;rel=\"knowledgeVOList\""));
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
		Integer id = 2;
		
		Knowledge mockEntity = input.entity(id);
		Knowledge persistedEntity = mockEntity;
		KnowledgeVO mockVO = input.vo(id);
		mockVO.setTitle(id + "Title");
		mockVO.setDescription(id + "Description");
		mockVO.setContent(id + "Content");
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		KnowledgeVO updatedKnowledge = service.updateById(id, mockVO);
		
		assertNotNull(updatedKnowledge);
		
		assertEquals(2, updatedKnowledge.getKey());
		assertEquals("2Title", updatedKnowledge.getTitle());
		assertEquals("2Description", updatedKnowledge.getDescription());
		assertEquals("2Content", updatedKnowledge.getContent());
		
		assertEquals("Esti", updatedKnowledge.getSoftware().getName());
		
		assertEquals("Name0", updatedKnowledge.getTopics().get(0).getName());
		assertEquals("Name1", updatedKnowledge.getTopics().get(1).getName());
		
		assertTrue(updatedKnowledge.getLinks().toString().contains("</v1/knowledge?page=0&size=10&sortBy=title&direction=asc>;rel=\"knowledgeVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		KnowledgeVO mockVO = input.vo();
		
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
		KnowledgeVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Knowledge entity = input.entity(1); 
		
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
