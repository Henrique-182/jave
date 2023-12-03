package br.com.ibpt.unittests.services.v2;

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

import br.com.ibpt.data.vo.v2.CompanyVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v2.CompanyMapper;
import br.com.ibpt.model.v2.Company;
import br.com.ibpt.model.v2.CompanySoftware;
import br.com.ibpt.repositories.v2.CompanyRepository;
import br.com.ibpt.services.v2.CompanyService;
import br.com.ibpt.unittests.mocks.v2.CompanyMock;

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
		Company mockEntity = input.mockEntity(2);
		Company persisted = mockEntity;
		CompanyVO mockVO = input.mockVO(2);
		
		when(mapper.toEntity(any(CompanyVO.class))).thenReturn(mockEntity);
		when(repository.save(any(Company.class))).thenReturn(persisted);
		when(mapper.toVO(any(Company.class))).thenReturn(mockVO);
		
		CompanyVO createdCompany = service.create(mockVO);
		
		assertEquals(2, createdCompany.getKey());
		assertEquals("22222222222222", createdCompany.getCnpj());
		assertEquals("Trade Name2", createdCompany.getTradeName());
		assertEquals("Business Name2", createdCompany.getBusinessName());
		assertEquals("Observation2", createdCompany.getObservation());
		assertEquals(true, createdCompany.getIsActive());
		
		List<CompanySoftware> createdCompanySoftware = createdCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = createdCompanySoftware.get(0);
		
		assertEquals(2, companySoftwareOne.getId());
		assertEquals("Name2", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = createdCompanySoftware.get(1);
		
		assertEquals(4, companySoftwareTwo.getId());
		assertEquals("Name2", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
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
		
		Company mockEntity = input.mockEntity(id);
		CompanyVO mockVO = input.mockVO(id);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(any(Company.class))).thenReturn(mockVO);
		
		CompanyVO persistedCompany = service.findById(id);
		
		assertEquals(2, persistedCompany.getKey());
		assertEquals("22222222222222", persistedCompany.getCnpj());
		assertEquals("Trade Name2", persistedCompany.getTradeName());
		assertEquals("Business Name2", persistedCompany.getBusinessName());
		assertEquals("Observation2", persistedCompany.getObservation());
		assertEquals(true, persistedCompany.getIsActive());
		
		List<CompanySoftware> persistedCompanySoftware = persistedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = persistedCompanySoftware.get(0);
		
		assertEquals(2, companySoftwareOne.getId());
		assertEquals("Name2", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = persistedCompanySoftware.get(1);
		
		assertEquals(4, companySoftwareTwo.getId());
		assertEquals("Name2", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		
		CompanyVO mockVO = input.mockVO(id);
		mockVO.setTradeName(id + "Trade Name");
		mockVO.setBusinessName(id + "Business Name");
		mockVO.setObservation(id + "Observation");
		
		Company mockEntity = input.mockEntity();
		Company persisted = mockEntity;
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(repository.save(any(Company.class))).thenReturn(persisted);
		when(mapper.toVO(any(Company.class))).thenReturn(mockVO);
		
		CompanyVO updatedCompany = service.updateById(id, mockVO);
		
		assertEquals(2, updatedCompany.getKey());
		assertEquals("22222222222222", updatedCompany.getCnpj());
		assertEquals("2Trade Name", updatedCompany.getTradeName());
		assertEquals("2Business Name", updatedCompany.getBusinessName());
		assertEquals("2Observation", updatedCompany.getObservation());
		assertEquals(true, updatedCompany.getIsActive());
		
		List<CompanySoftware> updatedCompanySoftware = updatedCompany.getSoftwares();
		
		CompanySoftware companySoftwareOne = updatedCompanySoftware.get(0);
		
		assertEquals(2, companySoftwareOne.getId());
		assertEquals("Name2", companySoftwareOne.getSoftware().getName());
		assertEquals("Fiscal", companySoftwareOne.getType());
		assertEquals(true, companySoftwareOne.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareOne.getConnection());
		assertEquals("Observation2", companySoftwareOne.getObservation());
		assertEquals(true, companySoftwareOne.getIsActive());
		assertEquals(null, companySoftwareOne.getFkCompanySoftwareSameDb());
		
		CompanySoftware companySoftwareTwo = updatedCompanySoftware.get(1);
		
		assertEquals(4, companySoftwareTwo.getId());
		assertEquals("Name2", companySoftwareTwo.getSoftware().getName());
		assertEquals("Geral", companySoftwareTwo.getType());
		assertEquals(true, companySoftwareTwo.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareTwo.getConnection());
		assertEquals("Observation2", companySoftwareTwo.getObservation());
		assertEquals(true, companySoftwareTwo.getIsActive());
		assertEquals(null, companySoftwareTwo.getFkCompanySoftwareSameDb());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Integer id = null;
		CompanyVO mockVO = input.mockVO();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, mockVO);
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
		Company entity = input.mockEntity(1); 
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		service.deleteById(1);
	}
}
