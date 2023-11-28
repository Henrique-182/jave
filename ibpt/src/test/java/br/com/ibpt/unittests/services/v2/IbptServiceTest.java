package br.com.ibpt.unittests.services.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

import br.com.ibpt.data.vo.v2.IbptUpdateVO;
import br.com.ibpt.data.vo.v2.IbptVO;
import br.com.ibpt.mappers.v2.IbptMapper;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.model.v2.CompanyIbpt;
import br.com.ibpt.model.v2.CompanySoftwareIbpt;
import br.com.ibpt.model.v2.Ibpt;
import br.com.ibpt.model.v2.SoftwareIbpt;
import br.com.ibpt.repositories.v2.IbptRepository;
import br.com.ibpt.services.v2.IbptService;
import br.com.ibpt.unittests.mocks.v2.IbptMock;

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
		
		IbptVO mockVO = input.mockVO(id);
		
		when(repository.findVersionById(any(Integer.class))).thenReturn(id);
		when(repository.findCompanySoftwareById(any(Integer.class))).thenReturn(id);
		repository.updateByVersionAndCompanySoftware(any(Integer.class), any(Integer.class), any(Boolean.class));
		
		service.updateById(data);
		
		assertEquals(2, mockVO.getKey());
		assertEquals(true, mockVO.getIsUpdated());
		
		Version versionIbpt = mockVO.getVersion();
		
		assertEquals(2, versionIbpt.getId());
		assertEquals("Name2", versionIbpt.getName());
		assertEquals(new Date(2), versionIbpt.getEffectivePeriodUntil());
		
		CompanySoftwareIbpt companySoftwareIbpt = mockVO.getCompanySoftware();
		
		assertEquals(2, companySoftwareIbpt.getId());
		assertEquals("Fiscal", companySoftwareIbpt.getType());
		assertEquals(true, companySoftwareIbpt.getHaveAuthorization());
		assertEquals("Connection2", companySoftwareIbpt.getConnection());
		assertEquals("Observation2", companySoftwareIbpt.getObservation());
		assertEquals(true, companySoftwareIbpt.getIsActive());
		assertEquals(null, companySoftwareIbpt.getFkCompanySoftwareSameDb());
		
		SoftwareIbpt softwareIbpt = companySoftwareIbpt.getSoftware();
		
		assertEquals(2, softwareIbpt.getId());
		assertEquals("Name2", softwareIbpt.getName());
		
		CompanyIbpt companyIbpt = companySoftwareIbpt.getCompany();
		
		assertEquals(2, companyIbpt.getId());
		assertEquals("Trade Name2", companyIbpt.getTradeName());
		assertEquals("Business Name2", companyIbpt.getBusinessName());
		assertEquals("Observation2", companyIbpt.getObservation());
		assertEquals(true, companyIbpt.getIsActive());
	}
	
	@Test
	void testDeleteById() {
		Ibpt entity = input.mockEntity(1); 
		
		when(repository.findById(1)).thenReturn(Optional.of(entity));
		
		service.deleteById(1);
	}
}
