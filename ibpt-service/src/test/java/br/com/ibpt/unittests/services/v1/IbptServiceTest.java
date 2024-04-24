package br.com.ibpt.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ibpt.data.vo.v1.IbptUpdateVO;
import br.com.ibpt.data.vo.v1.IbptVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.IbptMapper;
import br.com.ibpt.model.v1.CompanyIbpt;
import br.com.ibpt.model.v1.CompanySoftwareIbpt;
import br.com.ibpt.model.v1.Ibpt;
import br.com.ibpt.model.v1.SoftwareIbpt;
import br.com.ibpt.model.v1.VersionIbpt;
import br.com.ibpt.repositories.v1.IbptRepository;
import br.com.ibpt.services.v1.IbptService;
import br.com.ibpt.unittests.mocks.v1.IbptMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class IbptServiceTest {

	IbptMock input;
	
	@Autowired
	@InjectMocks
	IbptService service;
	
	@Mock
	IbptRepository repository;
	
	@Mock
	IbptMapper mapper;
	
	@BeforeEach
	void setup() {
		input = new IbptMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		
		IbptUpdateVO data = new IbptUpdateVO();
		data.setKey(id);
		data.setValue(true);
		
		Ibpt mockEntity = input.mockEntity(id);
		IbptVO mockVO = input.mockVO(id);
		
		when(repository.findById(data.getKey())).thenReturn(Optional.of(mockEntity));
		repository.updateByVersionAndCompanySoftware(any(Integer.class), any(Integer.class), any(Boolean.class), any(Date.class));
		
		service.updateById(data);
		
		assertEquals(2, mockVO.getKey());
		assertEquals(true, mockVO.getIsUpdated());
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(mockVO.getLastUpdateDatetime()));
		assertEquals(DateFormat.getDateInstance().format(new Date()), DateFormat.getDateInstance().format(mockVO.getCreationDatetime()));
		
		VersionIbpt versionIbpt = mockVO.getVersion();
		
		assertEquals(2, versionIbpt.getId());
		assertEquals("Name2", versionIbpt.getName());
		
		CompanySoftwareIbpt companySoftwareIbpt = mockVO.getCompanySoftware();
		
		assertEquals(2, companySoftwareIbpt.getKey());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareIbpt.getConnection());
		assertEquals("Observation2", companySoftwareIbpt.getObservation());
		assertEquals(null, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getKey());
		assertEquals("Name2", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(2, companyIbpt.getId());
		assertEquals("Trade Name2", companyIbpt.getTradeName());
		assertEquals("Business Name2", companyIbpt.getBusinessName());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		IbptUpdateVO data = new IbptUpdateVO();
		data.setKey(1000);
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(data);
		});
		
		String expectedMessage = "No records found for the id (" + data.getKey() + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateByIdWithRequiredObjectIsNullException() {
		IbptUpdateVO data = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(data);
		});
		
		String expectedMessage = "It is not possible to update a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Ibpt entity = input.mockEntity(1); 
		
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
