package br.com.conhecimento.integrationtests.repositories.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.conhecimento.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.conhecimento.model.v1.Knowledge;
import br.com.conhecimento.repositories.v1.KnowledgeCustomRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class KnowledgeRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private KnowledgeCustomRepository repository;
	
	@Test
	@Order(1)
	void testFindPageableCustom() {
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "title"));
		
		Map<String, Object> params = Map.of(
			"knowledgeTitle", "TG",
			"knowledgeDescription", "",
			"knowledgeContent", ""
		);
		
		@SuppressWarnings({ "unchecked" })
		List<Knowledge> resultList = (List<Knowledge>) repository
				.findCustomPageable(pageable, params)
				.get("resultList");
		
		Knowledge knowledgeZero = resultList.get(0);
		
		assertEquals(1, knowledgeZero.getId());
		assertEquals("TG Config", knowledgeZero.getTitle());
		assertEquals("TG Config", knowledgeZero.getDescription());
		assertTrue(knowledgeZero.getContent().toString().contains("<TGAPLICACAO name=\"Arca\" Caption=\"ARCA - Automação de Comércio e Indústria\">"));
		
		assertEquals("Stac", knowledgeZero.getSoftware().getName());
	}
	
}
