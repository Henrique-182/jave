package br.com.ibpt.integrationtests.repositories.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ibpt.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.repositories.v1.VersionCustomRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class VersionCustomRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private VersionCustomRepository customRepository;
	
	private static Version version;
	
	@BeforeAll
	public static void setup() {
		version = new Version();
	}
	
	@Test
	@Order(1)
	public void testFindCustom() throws ParseException {
		
		String name = "23";
		String effectivePeriodMonth = null;
		String effectivePeriodYear = null;
		String sortBy = null;
		String direction = "desc";
		
		List<Version> entityList = customRepository.findCustom(name, effectivePeriodMonth, effectivePeriodYear, sortBy, direction);
		
		version = entityList.get(0);
		
		assertEquals(5, version.getId());
		assertEquals("23.2.F", version.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-31"), version.getEffectivePeriodUntil());
	}
	
}
