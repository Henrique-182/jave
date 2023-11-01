package br.com.ibpt.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.ibpt.data.vo.v1.CompanyVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.CompanyMapper;
import br.com.ibpt.model.v1.Company;
import br.com.ibpt.repositories.v1.CompanyCustomRepository;
import br.com.ibpt.repositories.v1.CompanyRepository;
import br.com.ibpt.repositories.v1.VersionRepository;
import br.com.ibpt.services.v1.CompanyService;
import br.com.ibpt.services.v1.IbptUpdateService;
import br.com.ibpt.unittests.mocks.v1.MockCompany;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class CompanyServiceTests {

	MockCompany input;
	
	@Autowired
	@InjectMocks
	CompanyService companyService;
	
	@Mock
	IbptUpdateService ibptUpdateService;
	
	@Mock
	CompanyRepository companyRepository;
	
	@Mock
	CompanyCustomRepository companyCustomRepository;
	
	@Spy
	CompanyMapper companyMapper;
	
	@Spy
	VersionRepository versionRepository;
	
	@BeforeEach
	void setUp() {
		input = new MockCompany();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindById() {
		Integer id = 5;
		Company mockEntity = input.mockEntity(id);
		CompanyVO mockVO = input.mockVO(id);
		
		when(companyRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(companyMapper.toCompanyVO(any(Company.class))).thenReturn(mockVO);
		
		CompanyVO result = companyService.findById(id);
		
		assertNotNull(result);
		assertEquals(id, result.getKey());
		assertEquals("55555555555555", result.getCnpj());
		assertEquals("Trade Name5", result.getTradeName());
		assertEquals("Business Name5", result.getBusinessName());
		assertEquals("Esti", result.getSoftware());
		assertEquals(false, result.getHaveAuthorization());
		assertEquals("555555555", result.getConnection());
		assertEquals("Observation5", result.getObservation());
		assertEquals(false, result.getIsActive());
		assertEquals(25, result.getFkCompanySameDb());
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			companyService.findById(null);
		});
		
		String expectedMessage = "No records found for this ID!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testFindCustom() {
		String cnpj = "";
		String tradeName = "";
		String businessName = "";
		String software = "";
		String connection = "";
		Boolean haveAuthorization = true;
		Boolean isActive = true;
		Integer fkCompanySameDb = 0;
		
		List<Company> entityList = input.mockEntityList();
		List<CompanyVO> voList = input.mockVOList();
		
		when(companyCustomRepository.findCustom(any(String.class), any(String.class), any(String.class), any(String.class), any(String.class), any(Boolean.class), any(Boolean.class), any(Integer.class))).thenReturn(entityList);
		when(companyMapper.toCompanyVOList(anyList())).thenReturn(voList);
		
		List<CompanyVO> resultList = companyService.findCustom(cnpj, tradeName, businessName, software, connection, haveAuthorization, isActive, fkCompanySameDb);
		
		assertNotNull(resultList);
		assertEquals(resultList, voList);
	}
	
	@Test
	void testCreate() {
		Company mockEntity = input.mockEntity();
		Company persisted = mockEntity;
		CompanyVO mockVO = input.mockVO();
		
		when(companyMapper.toCompany(any(CompanyVO.class))).thenReturn(mockEntity);
		when(companyRepository.save(any(Company.class))).thenReturn(persisted);
		when(companyMapper.toCompanyVO(any(Company.class))).thenReturn(mockVO);
		
		CompanyVO result = companyService.create(mockVO);
		
		assertNotNull(result);
		assertEquals(0, result.getKey());
		assertEquals("00000000000000", result.getCnpj());
		assertEquals("Trade Name0", result.getTradeName());
		assertEquals("Business Name0", result.getBusinessName());
		assertEquals("Stac", result.getSoftware());
		assertEquals(true, result.getHaveAuthorization());
		assertEquals("000000000", result.getConnection());
		assertEquals("Observation0", result.getObservation());
		assertEquals(true, result.getIsActive());
		assertEquals(0, result.getFkCompanySameDb());
	}
	
	@Test
	void testCreateWithNullObject() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			companyService.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById() {
		Integer id = 2;
		CompanyVO mockVO = input.mockVO(2);
		mockVO.setCnpj("2222222222");
		mockVO.setTradeName(id + "Trade Name");
		mockVO.setBusinessName(id + "Business Name");
		mockVO.setSoftware("Esti");
		mockVO.setHaveAuthorization(false);
		mockVO.setConnection("" + id + id + id);
		mockVO.setObservation(id + "Observation");
		mockVO.setIsActive(false);
		mockVO.setFkCompanySameDb(id + id);
		
		Company mockEntity = input.mockEntity(2);
		Company persisted = mockEntity;
		
		when(companyRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(companyRepository.save(any(Company.class))).thenReturn(persisted);
		when(companyMapper.toCompanyVO(any(Company.class))).thenReturn(mockVO);
		
		var result = companyService.updateById(mockVO, id);
		
		assertNotNull(result);
		assertEquals(2, result.getKey());
		assertEquals("2222222222", result.getCnpj());
		assertEquals("2Trade Name", result.getTradeName());
		assertEquals("2Business Name", result.getBusinessName());
		assertEquals("Esti", result.getSoftware());
		assertEquals(false, result.getHaveAuthorization());
		assertEquals("222", result.getConnection());
		assertEquals("2Observation", result.getObservation());
		assertEquals(false, result.getIsActive());
		assertEquals(4, result.getFkCompanySameDb());
	}
	
	@Test
	void testUpdateByIdResourceNotFoundException() {
		CompanyVO mockVO = input.mockVO(2);
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			companyService.updateById(mockVO, null);
		});
		
		String expectedMessage = "No records found for this ID!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
}
