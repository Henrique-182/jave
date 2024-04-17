package br.com.conhecimento.integrationtests.repositories.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.model.v1.Topic;
import br.com.conhecimento.repositories.v1.TopicRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class TopicRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private TopicRepository repository;
	
	@Test
	@Order(1)
	void testFindByNamePageable() {
		
		String topicName = "Ce";
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "name"));
		
		Page<Topic> pageResult = repository.findPageableByNameContaining(topicName, pageable);
		
		assertEquals(10, pageResult.getSize());
		assertEquals(3, pageResult.getTotalElements());
		assertEquals(1, pageResult.getTotalPages());
		assertEquals(0, pageResult.getNumber());
		
		List<Topic> resultList = pageResult.getContent();
		
		Topic topicOne = resultList.get(0);
		
		assertEquals(14, topicOne.getId());
		assertEquals("Certificado", topicOne.getName());
		
		Topic topicTwo = resultList.get(1);
		
		assertEquals(7, topicTwo.getId());
		assertEquals("Financeiro", topicTwo.getName());
	}
	
}
