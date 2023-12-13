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
import br.com.ibpt.repositories.v2.IbptRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class IbptRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	public IbptRepository repository;
	
	private static Ibpt ibpt;
	
	@BeforeAll
	public static void setup() {
		ibpt = new Ibpt();
	}
	
	@Test
	@Order(1)
	public void testCallProcNewIbpt() throws ParseException {
		ibpt = repository.findById(1).orElseThrow();
		
		repository.deleteById(1);
		
		repository.findAll();
		
		repository.callProcNewIbpt(ibpt.getVersion().getId());
		
		List<Ibpt> persistedList = repository.findAll();
		
		ibpt = persistedList.get(persistedList.size() - 1);

		assertTrue(ibpt.getId() > 0);
		assertFalse(ibpt.getIsUpdated());
		
		Version versionIbpt = ibpt.getVersion();
		
		assertEquals(5, versionIbpt.getId());
		assertEquals("23.2.F", versionIbpt.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-31"), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibpt.getCompanySoftware();
		
		assertEquals(1, companySoftwareIbpt.getId());
		assertEquals("FISCAL", companySoftwareIbpt.getType());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("818998995", companySoftwareIbpt.getConnection());
		assertEquals("", companySoftwareIbpt.getObservation());
		assertEquals(true, companySoftwareIbpt.getIsActive());
		assertEquals(null, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(1, companyIbpt.getId());
		assertEquals("AGROSOLO", companyIbpt.getTradeName());
		assertEquals("AGROSOLO SOLUÇÕES AGRÍCOLAS LTDA", companyIbpt.getBusinessName());
		assertEquals("", companyIbpt.getObservation());
		assertEquals(true, companyIbpt.getIsActive());
	}
	
	@Test
	@Order(2)
	public void testUpdateByVersionAndCompanySoftware() throws ParseException {
		
		ibpt = repository.findById(1).orElseThrow();
		
		repository.delete(ibpt);
		
		repository.findAll();
		
		repository.callProcNewIbpt(ibpt.getVersion().getId());
		
		repository.updateByVersionAndCompanySoftware(ibpt.getVersion().getId(), ibpt.getCompanySoftware().getId(), true);
		
		List<Ibpt> persistedList = repository.findAll();
		
		ibpt = persistedList.get(persistedList.size() - 1);
		
		assertTrue(ibpt.getId() > 0);
		assertTrue(ibpt.getIsUpdated());
		
		Version versionIbpt = ibpt.getVersion();
		
		assertEquals(5, versionIbpt.getId());
		assertEquals("23.2.F", versionIbpt.getName());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-31"), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibpt.getCompanySoftware();
		
		assertEquals(1, companySoftwareIbpt.getId());
		assertEquals("FISCAL", companySoftwareIbpt.getType());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("818998995", companySoftwareIbpt.getConnection());
		assertEquals("", companySoftwareIbpt.getObservation());
		assertEquals(true, companySoftwareIbpt.getIsActive());
		assertEquals(null, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(1, companyIbpt.getId());
		assertEquals("AGROSOLO", companyIbpt.getTradeName());
		assertEquals("AGROSOLO SOLUÇÕES AGRÍCOLAS LTDA", companyIbpt.getBusinessName());
		assertEquals("", companyIbpt.getObservation());
		assertEquals(true, companyIbpt.getIsActive());
		
	}

}
