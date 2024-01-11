package br.com.sistemas.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.sistemas.data.vo.v1.CompanyVO;
import br.com.sistemas.exceptions.v1.RequiredObjectIsNullException;
import br.com.sistemas.exceptions.v1.ResourceNotFoundException;
import br.com.sistemas.mappers.v1.CompanyMapper;
import br.com.sistemas.model.v1.Company;
import br.com.sistemas.model.v1.CompanySoftware;
import br.com.sistemas.model.v1.SoftwareType;
import br.com.sistemas.repositories.v1.CompanyRepository;
import br.com.sistemas.services.v1.CompanyService;
import br.com.sistemas.unittests.mock.v1.CompanyMock;


@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

	CompanyMock input;
	
	@Autowired
	@InjectMocks
	CompanyService service;
	
	@Mock
	CompanyRepository repository;
	
	@Mock
	CompanyMapper mapper;
	
	@BeforeEach
	void setup() {
		input = new CompanyMock();
	}
	
	@Test
	void testCreate() {
		Company entity = input.entity(2);
		Company persisted = entity;
		CompanyVO vo = input.vo(2);
		
		when(mapper.toEntity(any(CompanyVO.class))).thenReturn(entity);
		when(repository.save(any(Company.class))).thenReturn(persisted);
		when(mapper.toVO(any(Company.class))).thenReturn(vo);
		
		CompanyVO createdCompany = service.create(vo);
		
		assertEquals(2, createdCompany.getKey());
		assertEquals("22222222222222", createdCompany.getCnpj());
		assertEquals("Trade Name2", createdCompany.getTradeName());
		assertEquals("Business Name2", createdCompany.getBusinessName());
		assertEquals("Observation2", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		assertEquals("</v1/company?page=0&size=10&sortBy=asc&direction=name>;rel=\"companyVOList\"", createdCompany.getLinks().toString());
		
		List<CompanySoftware> createdCompanySoftware = createdCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = createdCompanySoftware.get(0);
		
		assertEquals(2, companySoftwareOne.getKey());
		assertEquals("Name2", companySoftwareOne.getSoftware().getName());
		assertEquals(SoftwareType.Fiscal, companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = createdCompanySoftware.get(1);
		
		assertEquals(4, companySoftwareTwo.getKey());
		assertEquals("Name2", companySoftwareTwo.getSoftware().getName());
		assertEquals(SoftwareType.Geral, companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testFindById() {
		Integer id = 2;
		
		Company entity = input.entity(id);
		CompanyVO vo = input.vo(id);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(entity));
		when(mapper.toVO(any(Company.class))).thenReturn(vo);
		
		CompanyVO persistedCompany = service.findById(id);
		
		assertEquals(2, persistedCompany.getKey());
		assertEquals("22222222222222", persistedCompany.getCnpj());
		assertEquals("Trade Name2", persistedCompany.getTradeName());
		assertEquals("Business Name2", persistedCompany.getBusinessName());
		assertEquals("Observation2", persistedCompany.getObservation());
		assertEquals(true, persistedCompany.getIsActive());
		assertEquals("</v1/company?page=0&size=10&sortBy=asc&direction=name>;rel=\"companyVOList\"", persistedCompany.getLinks().toString());
		
		List<CompanySoftware> persistedCompanySoftware = persistedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = persistedCompanySoftware.get(0);
		
		assertEquals(2, companySoftwareOne.getKey());
		assertEquals("Name2", companySoftwareOne.getSoftware().getName());
		assertEquals(SoftwareType.Fiscal, companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = persistedCompanySoftware.get(1);
		
		assertEquals(4, companySoftwareTwo.getKey());
		assertEquals("Name2", companySoftwareTwo.getSoftware().getName());
		assertEquals(SoftwareType.Geral, companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Integer id = 10;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		
		CompanyVO vo = input.vo(id);
		vo.setTradeName(id + "Trade Name");
		vo.setBusinessName(id + "Business Name");
		vo.setObservation(id + "Observation");
		
		Company entity = input.entity();
		Company persisted = entity;
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(entity));
		when(repository.save(any(Company.class))).thenReturn(persisted);
		when(mapper.toVO(any(Company.class))).thenReturn(vo);
		
		CompanyVO updatedCompany = service.updateById(id, vo);
		
		assertEquals(2, updatedCompany.getKey());
		assertEquals("22222222222222", updatedCompany.getCnpj());
		assertEquals("2Trade Name", updatedCompany.getTradeName());
		assertEquals("2Business Name", updatedCompany.getBusinessName());
		assertEquals("2Observation", updatedCompany.getObservation());
		assertEquals(true, updatedCompany.getIsActive());
		assertEquals("</v1/company?page=0&size=10&sortBy=asc&direction=name>;rel=\"companyVOList\"", updatedCompany.getLinks().toString());
		
		List<CompanySoftware> updatedCompanySoftware = updatedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = updatedCompanySoftware.get(0);
		
		assertEquals(2, companySoftwareOne.getKey());
		assertEquals("Name2", companySoftwareOne.getSoftware().getName());
		assertEquals(SoftwareType.Fiscal, companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = updatedCompanySoftware.get(1);
		
		assertEquals(4, companySoftwareTwo.getKey());
		assertEquals("Name2", companySoftwareTwo.getSoftware().getName());
		assertEquals(SoftwareType.Geral, companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		CompanyVO vo = input.vo();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, vo);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateByIdWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(2, null);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Company entity = input.entity(1); 
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		service.deleteById(1);
	}
	
	@Test
	void testDeleteByIdWithResourceNotFoundException() {
		Integer id = 10;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
}
