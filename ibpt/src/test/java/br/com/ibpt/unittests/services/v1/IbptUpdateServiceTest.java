package br.com.ibpt.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

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
import br.com.ibpt.repositories.v1.IbptUpdateCustomRepository;
import br.com.ibpt.repositories.v1.IbptUpdateRepository;
import br.com.ibpt.services.v1.IbptUpdateService;
import br.com.ibpt.unittests.mocks.v1.IbptUpdateMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class IbptUpdateServiceTest {
	
	IbptUpdateMock input;

	@Autowired
	@InjectMocks
	IbptUpdateService ibptUpdateService;
	
	@Mock
	IbptUpdateRepository ibptUpdateRepository;
	
	@Mock
	IbptUpdateCustomRepository ibptUpdateCustomRepository;
	
	@BeforeEach
	void setUp() {
		input = new IbptUpdateMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindCustom() {
		String versionName = "";
		String companyCnpj = "";
		String companyBusinessName = "";
		String companyTradeName = "";
		Boolean isUpdated = true;
		
		List<Object[]> mockObjectList = input.mockObjectList();
		
		when(ibptUpdateCustomRepository.findCustom(
						any(String.class), 
						any(String.class), 
						any(String.class), 
						any(String.class), 
						any(Boolean.class)
						)
		).thenReturn(mockObjectList);
		
		
		List<IbptUpdateVO> resultList = ibptUpdateService.findCustom(
			versionName, 
			companyCnpj, 
			companyBusinessName, 
			companyTradeName, 
			isUpdated
		);
		
		assertNotNull(resultList);
		
		IbptUpdateVO resultOne = resultList.get(1);
		
		assertEquals(1, resultOne.getKey());
		assertEquals("11111111111111", resultOne.getCompanyCnpj());
		assertEquals("Company Trade Name1", resultOne.getCompanyTradeName());
		assertEquals("Company Business Name1", resultOne.getCompanyBusinessName());
		assertEquals(false, resultOne.getIsUpdated());
		
		IbptUpdateVO resultTwo = resultList.get(2);
		
		assertEquals(2, resultTwo.getKey());
		assertEquals("22222222222222", resultTwo.getCompanyCnpj());
		assertEquals("Company Trade Name2", resultTwo.getCompanyTradeName());
		assertEquals("Company Business Name2", resultTwo.getCompanyBusinessName());
		assertEquals(true, resultTwo.getIsUpdated());
	}
	
}
