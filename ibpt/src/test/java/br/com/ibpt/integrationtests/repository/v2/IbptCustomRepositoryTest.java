package br.com.ibpt.integrationtests.repository.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.ibpt.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.model.v2.CompanyIbpt;
import br.com.ibpt.model.v2.CompanySoftwareIbpt;
import br.com.ibpt.model.v2.Ibpt;
import br.com.ibpt.model.v2.SoftwareIbpt;
import br.com.ibpt.repositories.v2.IbptCustomRepository;

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
		
		Integer id = null;
		String versionName = "23.2.D";
		String companyCnpj = null;
		String companyName = "rainha";
		Boolean isUpdated = false;
		String sortBy = "companySoftware.company.tradeName";
		String direction = "DESC";
		
		List<Ibpt> entityList = customRepository.findCustom(id, versionName, companyCnpj, companyName, isUpdated, sortBy, direction);
		
		ibpt = entityList.get(0);
		
		assertTrue(ibpt.getId() > 0);
		assertFalse(ibpt.getIsUpdated());
		
		Version version = ibpt.getVersion();
		
		assertEquals(3, version.getId());
		assertEquals("23.2.D", version.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2023-10-31"), version.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibpt.getCompanySoftware();
		
		assertEquals(89, companySoftwareIbpt.getId());
		assertEquals("FISCAL", companySoftwareIbpt.getType());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("", companySoftwareIbpt.getConnection());
		assertEquals("", companySoftwareIbpt.getObservation());
		assertEquals(true, companySoftwareIbpt.getIsActive());
		assertEquals(35, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(89, companyIbpt.getId());
		assertEquals("37886462000168", companyIbpt.getCnpj());
		assertEquals("RAINHA MATRIZ", companyIbpt.getTradeName());
		assertEquals("RAINHA DA BORRACHA LTDA", companyIbpt.getBusinessName());
		assertEquals("", companyIbpt.getObservation());
		assertEquals(true, companyIbpt.getIsActive());
	}
	
}
