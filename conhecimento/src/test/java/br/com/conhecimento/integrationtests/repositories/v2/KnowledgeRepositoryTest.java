package br.com.conhecimento.integrationtests.repositories.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.util.Date;
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
import br.com.conhecimento.model.v2.Knowledge;
import br.com.conhecimento.repositories.v2.KnowledgeRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class KnowledgeRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private KnowledgeRepository repository;
	
	@Test
	@Order(1)
	void testFindPageableCustom() {
		
		String title = "tg";
		String description = "";
		String content = "";
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "title"));
		
		Page<Knowledge> pageResult = repository
				.findPageableByTitleContainingAndDescriptionContainingAndContentContaining(title, description, content, pageable);
		
		List<Knowledge> resultList = pageResult.getContent();
		
		Knowledge knowledgeZero = resultList.get(0);
		
		assertEquals(1, knowledgeZero.getId());
		assertEquals("TG Config", knowledgeZero.getTitle());
		assertEquals("TG Config", knowledgeZero.getDescription());
		assertTrue(knowledgeZero.getContent().toString().contains("<TGAPLICACAO name=\"Arca\" Caption=\"ARCA - Automação de Comércio e Indústria\">"));
		
		assertEquals("Stac", knowledgeZero.getSoftware().getName());
		
		assertEquals("henrique", knowledgeZero.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(knowledgeZero.getLastUpdateDatetime()));
		assertEquals("henrique", knowledgeZero.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(knowledgeZero.getCreationDatetime()));
	}
	
}
