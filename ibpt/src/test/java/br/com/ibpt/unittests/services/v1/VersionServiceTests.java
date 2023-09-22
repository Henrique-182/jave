package br.com.ibpt.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.Date;
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

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.VersionMapper;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.repositories.v1.VersionCustomRepository;
import br.com.ibpt.repositories.v1.VersionRepository;
import br.com.ibpt.services.v1.VersionService;
import br.com.ibpt.unittests.mocks.v1.VersionMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VersionServiceTests {

	VersionMock input;
	
	@Autowired
	@InjectMocks
	VersionService versionService;
	
	@Mock
	VersionRepository versionRepository;
	
	@Mock
	VersionCustomRepository versionCustomRepository;
	
	@Spy
	VersionMapper versionMapper;
	
	@BeforeEach
	void setUp() {
		input = new VersionMock();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindById() {
		Integer id = 1;

		Version mockEntity = input.mockEntity(1);
		VersionVO mockVO = input.mockVO(1);
		
		when(versionRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(versionMapper.toVersionVO(any(Version.class))).thenReturn(mockVO);
		
		VersionVO result = versionService.findById(id);
		
		assertNotNull(result);
		assertEquals(1, result.getKey());
		assertEquals("Name1", result.getName());
		assertEquals(new Date(1), result.getEffectivePeriodUntil());
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			versionService.findById(null);
		});
		
		String expectedMessage = "No records found for this id!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testFindCustom() {
		String name = "";
		String effectivePeriodMonth = "";
		String effectivePeriodYear = "";
		
		List<Version> entityList = input.mockEntityList();
		List<VersionVO> entityVO = input.mockVOList();
		
		when(versionCustomRepository.findCustom(any(String.class), any(String.class), any(String.class))).thenReturn(entityList);
		when(versionMapper.toVersionVOList(anyList())).thenReturn(entityVO);
		
		List<VersionVO> resultList = versionService.findCustom(name, effectivePeriodMonth, effectivePeriodYear);
		
		assertNotNull(resultList);
		assertEquals(resultList, entityVO);
	}
	
	@Test
	void testCreate() {
		Version mockEntity = input.mockEntity();
		Version persisted = mockEntity;
		VersionVO mockVO = input.mockVO();
		
		when(versionMapper.toVersion(any(VersionVO.class))).thenReturn(mockEntity);
		when(versionRepository.save(any(Version.class))).thenReturn(persisted);
		when(versionMapper.toVersionVO(any(Version.class))).thenReturn(mockVO);
		
		VersionVO result = versionService.create(mockVO);
		
		assertNotNull(result);
		assertEquals(0, result.getKey());
		assertEquals("Name0", result.getName());
		assertEquals(new Date(0), result.getEffectivePeriodUntil());
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			versionService.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdate() {
		Integer id = 2;
		
		Version mockEntity = input.mockEntity(2);
		Version persisted = mockEntity;
		
		VersionVO mockVO = input.mockVO(2);
		mockVO.setName(id + "Name");
		mockVO.setEffectivePeriodUntil(new Date(id + id));
		
		when(versionRepository.findById(any(Integer.class))).thenReturn(Optional.of(mockEntity));
		when(versionMapper.toVersion(any(VersionVO.class))).thenReturn(mockEntity);
		when(versionRepository.save(any(Version.class))).thenReturn(persisted);
		when(versionMapper.toVersionVO(any(Version.class))).thenReturn(mockVO);
		
		VersionVO result = versionService.updateById(mockVO, id);
		
		assertNotNull(result);
		assertEquals(2, result.getKey());
		assertEquals("2Name", result.getName());
		assertEquals(new Date(4), result.getEffectivePeriodUntil());
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		VersionVO mockVO = input.mockVO();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			versionService.updateById(mockVO, null);
		});
		
		String expectedMessage = "No records found for this id!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateByIdWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			versionService.updateById(null, 2);
		});
		
		String expectedMessage = "It is not possible to persist a null object";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
}
