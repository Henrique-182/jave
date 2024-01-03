package br.com.conhecimento.integrationtests.repositories.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import br.com.conhecimento.model.v2.Software;
import br.com.conhecimento.repositories.v1.SoftwareRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class SoftwareRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private SoftwareRepository repository;
	
	@Test
	@Order(1)
	void testFindByNamePageable() {
		
		String softwareName = "s";
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "name"));
		
		Page<Software> pageResult = repository.findPageableByNameContaining(softwareName, pageable);
		
		assertEquals(10, pageResult.getSize());
		assertEquals(2, pageResult.getTotalElements());
		assertEquals(1, pageResult.getTotalPages());
		assertEquals(0, pageResult.getNumber());
		
		List<Software> resultList = pageResult.getContent();
		
		Software softwareZero = resultList.get(0);
		
		assertEquals(1, softwareZero.getId());
		assertEquals("Esti", softwareZero.getName());
		
		assertEquals("henrique", softwareZero.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareZero.getLastUpdateDatetime()));
		assertEquals("henrique", softwareZero.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareZero.getCreationDatetime()));
		
		Software softwareOne = resultList.get(1);
		
		assertEquals(2, softwareOne.getId());
		assertEquals("Stac", softwareOne.getName());
		
		assertEquals("henrique", softwareOne.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareOne.getLastUpdateDatetime()));
		assertEquals("henrique", softwareOne.getUserCreation().getUsername());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(softwareOne.getCreationDatetime()));
	}
}
