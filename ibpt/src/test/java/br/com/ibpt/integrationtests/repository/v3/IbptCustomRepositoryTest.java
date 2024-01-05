package br.com.ibpt.integrationtests.repository.v3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.model.v3.CompanyIbpt;
import br.com.ibpt.model.v3.CompanySoftwareIbpt;
import br.com.ibpt.model.v3.Ibpt;
import br.com.ibpt.model.v3.SoftwareIbpt;
import br.com.ibpt.model.v3.VersionIbpt;
import br.com.ibpt.repositories.v3.IbptCustomRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class IbptCustomRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private IbptCustomRepository customRepository;
	
	private static Ibpt ibpt;
	
	@BeforeAll
	public static void setup() {
		ibpt = new Ibpt();
	}
	
	@Test
	@Order(1)
	public void testFindCustom() throws ParseException {
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.DESC, "companySoftware.company.tradeName"));
		String versionName = "23.2.D";
		String companyCnpj = null;
		String companyName = "rainha";
		Boolean isUpdated = false;
		
		List<Ibpt> entityList = customRepository.findCustom(pageable, versionName, companyCnpj, companyName, isUpdated);
		
		ibpt = entityList.get(0);
		
		assertTrue(ibpt.getId() > 0);
		assertFalse(ibpt.getIsUpdated());
		
		VersionIbpt version = ibpt.getVersion();
		
		assertEquals(3, version.getId());
		assertEquals("23.2.D", version.getName());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibpt.getCompanySoftware();
		
		assertEquals(89, companySoftwareIbpt.getId());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("", companySoftwareIbpt.getConnection());
		assertEquals("", companySoftwareIbpt.getObservation());
		assertEquals(35, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(89, companyIbpt.getId());
		assertEquals("37886462000168", companyIbpt.getCnpj());
		assertEquals("RAINHA MATRIZ", companyIbpt.getTradeName());
		assertEquals("RAINHA DA BORRACHA LTDA", companyIbpt.getBusinessName());
	}
	
}
