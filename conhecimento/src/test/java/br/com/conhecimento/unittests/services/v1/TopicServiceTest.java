package br.com.conhecimento.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v1.TopicMapper;
import br.com.conhecimento.model.v1.Topic;
import br.com.conhecimento.repositories.v1.TopicRepository;
import br.com.conhecimento.services.v1.TopicService;
import br.com.conhecimento.unittests.mocks.v1.TopicMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TopicServiceTest {

	TopicMock input;
	
	@Autowired
	@InjectMocks
	TopicService service;
	
	@Mock
	TopicRepository repository;
	
	@Mock
	TopicMapper mapper;

	@BeforeEach
	void setup() {
		input = new TopicMock();
	}
	
	@Test
	void testFindAll() {
		
		List<Topic> mockEntityList = input.entityList();
		List<TopicVO> mockVOList = input.voList();
		
		when(repository.findAll()).thenReturn(mockEntityList);
		when(mapper.toVOList(anyList())).thenReturn(mockVOList);
		
		List<TopicVO> resultList = service.findAll();
		
		assertNotNull(resultList);
		
		TopicVO topicOne = resultList.get(1);
		
		assertNotNull(topicOne);
		assertEquals(1, topicOne.getKey());
		assertEquals("Name1", topicOne.getName());
		assertTrue(topicOne.getLinks().toString().contains("</v1/topic/1>;rel=\"self\""));
		
		TopicVO topicTwo = resultList.get(2);
		
		assertNotNull(topicTwo);
		assertEquals(2, topicTwo.getKey());
		assertEquals("Name2", topicTwo.getName());
		assertTrue(topicTwo.getLinks().toString().contains("</v1/topic/2>;rel=\"self\""));
	}
	
	@Test
	void testFindById() {
		
		Integer id = 1;
		
		Topic mockEntity = input.entity(id);
		TopicVO mockVO = input.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		TopicVO persistedTopic = service.findById(id);
		
		assertNotNull(persistedTopic);
		
		assertNotNull(persistedTopic);
		assertEquals(1, persistedTopic.getKey());
		assertEquals("Name1", persistedTopic.getName());
		assertTrue(persistedTopic.getLinks().toString().contains("</v1/topic>;rel=\"VOList\""));
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
		
		Topic mockEntity = input.entity();
		Topic persistedEntity = mockEntity;
		TopicVO mockVO = input.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		TopicVO createdTopic = service.create(mockVO);
		
		assertNotNull(createdTopic);
		
		assertNotNull(createdTopic);
		assertEquals(0, createdTopic.getKey());
		assertEquals("Name0", createdTopic.getName());
		assertTrue(createdTopic.getLinks().toString().contains("</v1/topic>;rel=\"VOList\""));
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
		
		Topic mockEntity = input.entity(id);
		Topic persistedEntity = mockEntity;
		TopicVO mockVO = input.vo(id);
		mockVO.setName(id + "Name");
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		TopicVO updatedTopic = service.updateById(id, mockVO);
		
		assertNotNull(updatedTopic);
		
		assertNotNull(updatedTopic);
		assertEquals(2, updatedTopic.getKey());
		assertEquals("2Name", updatedTopic.getName());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		TopicVO mockVO = input.vo();
		
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
		TopicVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Topic entity = input.entity(1); 
		
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
