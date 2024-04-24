package br.com.ibpt.integrationtests.repositories.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.util.Date;
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

import br.com.ibpt.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.ibpt.model.v1.CompanyIbpt;
import br.com.ibpt.model.v1.CompanySoftwareIbpt;
import br.com.ibpt.model.v1.Ibpt;
import br.com.ibpt.model.v1.SoftwareIbpt;
import br.com.ibpt.model.v1.VersionIbpt;
import br.com.ibpt.repositories.v1.IbptRepository;

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
		repository.deleteByVersionId(1);
		
		List<Ibpt> persistedList = repository.findByVersionName("23.2.A");
		
		assertTrue(persistedList.size() == 0);	
		
		repository.selectFuncNewIbpt(1);
		
		persistedList = repository.findByVersionName("23.2.A");
		
		assertTrue(persistedList.size() > 0);
	}
	
	@Test
	@Order(2)
	public void testUpdateByVersionAndCompanySoftware() throws ParseException {
		
		repository.updateByVersionAndCompanySoftware(1, 1, true, new Date());
		
		ibpt = repository.findById(1).orElseThrow();
		
		assertTrue(ibpt.getId() > 0);
		assertTrue(ibpt.getIsUpdated());
		
		VersionIbpt versionIbpt = ibpt.getVersion();
		
		assertEquals(1, versionIbpt.getId());
		assertEquals("23.2.A", versionIbpt.getName());
		
		CompanySoftwareIbpt companySoftwareIbpt = ibpt.getCompanySoftware();
		
		assertEquals(1, companySoftwareIbpt.getKey());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("818998995", companySoftwareIbpt.getConnection());
		assertEquals("", companySoftwareIbpt.getObservation());
		assertEquals(null, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getKey());
		assertEquals("Stac", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(1, companyIbpt.getId());
		assertEquals("AGROSOLO", companyIbpt.getTradeName());
		assertEquals("AGROSOLO SOLUÇÕES AGRÍCOLAS LTDA", companyIbpt.getBusinessName());
		
	}

}
