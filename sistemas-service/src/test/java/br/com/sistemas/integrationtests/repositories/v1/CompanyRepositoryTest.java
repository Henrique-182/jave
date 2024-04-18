package br.com.sistemas.integrationtests.repositories.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import br.com.sistemas.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.sistemas.model.v1.Company;
import br.com.sistemas.model.v1.CompanySoftware;
import br.com.sistemas.model.v1.SoftwareType;
import br.com.sistemas.repositories.v1.CompanyRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class CompanyRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	public CompanyRepository repository;
	
	private static Company company;
	
	@BeforeAll
	public static void setup() {
		company = new Company();
	}
	
	@Test
	@Order(1)
	public void testUpdateCompanyIsActiveById() {
		Integer id = 1;
		Boolean value = false;
		
		repository.updateCompanyIsActiveById(id, value);
		
		company = repository.findById(id).orElseThrow();
		
		assertEquals(1, company.getId());
		assertEquals("26771869000123", company.getCnpj());
		assertEquals("AGROSOLO", company.getTradeName());
		assertEquals("AGROSOLO SOLUÇÕES AGRÍCOLAS LTDA", company.getBusinessName());
		assertEquals("", company.getObservation());
		assertEquals(false, company.getIsActive());
		
		CompanySoftware companySoftware = company.getSoftwares().get(0);
		
		assertEquals(1, companySoftware.getKey());
		assertEquals("Stac", companySoftware.getSoftware().getName());
		assertEquals(SoftwareType.Fiscal, companySoftware.getType());
		assertEquals(true, companySoftware.getHaveAuthorization());
		assertEquals("818998995", companySoftware.getConnection());
		assertEquals("", companySoftware.getObservation());
		assertEquals(true, companySoftware.getIsActive());
		assertEquals(null, companySoftware.getFkCompanySoftwareSameDb());
		
	}
	
	@Test
	@Order(2)
	public void testUpdateCompanySoftwareIsActiveByFkCompany() {
		Integer id = 1;
		Boolean value = true;
		
		repository.updateCompanySoftwareIsActiveByFkCompany(id, value);
		
		company = repository.findById(id).orElseThrow();
		
		assertEquals(1, company.getId());
		assertEquals("26771869000123", company.getCnpj());
		assertEquals("AGROSOLO", company.getTradeName());
		assertEquals("AGROSOLO SOLUÇÕES AGRÍCOLAS LTDA", company.getBusinessName());
		assertEquals("", company.getObservation());
		assertEquals(true, company.getIsActive());
		
		CompanySoftware companySoftware = company.getSoftwares().get(0);
		
		assertEquals(1, companySoftware.getKey());
		assertEquals("Stac", companySoftware.getSoftware().getName());
		assertEquals(SoftwareType.Fiscal, companySoftware.getType());
		assertEquals(true, companySoftware.getHaveAuthorization());
		assertEquals("818998995", companySoftware.getConnection());
		assertEquals("", companySoftware.getObservation());
		assertEquals(true, companySoftware.getIsActive());
		assertEquals(null, companySoftware.getFkCompanySoftwareSameDb());
		
	}
	
	@Test
	@Order(3)
	public void testUpdateCompanySoftwareIsActiveById() {
		Integer id = 1;
		Boolean value = false;
		
		repository.updateCompanySoftwareIsActiveById(id, value);
		
		company = repository.findById(id).orElseThrow();
		
		assertEquals(1, company.getId());
		assertEquals("26771869000123", company.getCnpj());
		assertEquals("AGROSOLO", company.getTradeName());
		assertEquals("AGROSOLO SOLUÇÕES AGRÍCOLAS LTDA", company.getBusinessName());
		assertEquals("", company.getObservation());
		assertEquals(true, company.getIsActive());
		
		CompanySoftware companySoftware = company.getSoftwares().get(0);
		
		assertEquals(1, companySoftware.getKey());
		assertEquals("Stac", companySoftware.getSoftware().getName());
		assertEquals(SoftwareType.Fiscal, companySoftware.getType());
		assertEquals(true, companySoftware.getHaveAuthorization());
		assertEquals("818998995", companySoftware.getConnection());
		assertEquals("", companySoftware.getObservation());
		assertEquals(false, companySoftware.getIsActive());
		assertEquals(null, companySoftware.getFkCompanySoftwareSameDb());
		
	}
	
}
