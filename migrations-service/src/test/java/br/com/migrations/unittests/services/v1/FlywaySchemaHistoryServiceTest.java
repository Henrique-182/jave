package br.com.migrations.unittests.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.migrations.controllers.v1.FlywaySchemaHistoryController;
import br.com.migrations.model.v1.FlywaySchemaHistory;
import br.com.migrations.repositories.v1.FlywaySchemaHistoryRepository;
import br.com.migrations.services.v1.FlywaySchemaHistoryService;
import br.com.migrations.unittests.mocks.v1.FlywaySchemaHistoryMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class FlywaySchemaHistoryServiceTest {

	FlywaySchemaHistoryMock input;
	
	@Autowired
	@InjectMocks
	FlywaySchemaHistoryService service;;
	
	@Mock
	FlywaySchemaHistoryRepository repository;
	
	@Mock
	FlywaySchemaHistoryController controller;
	
	@BeforeEach
	void setup() {
		input = new FlywaySchemaHistoryMock();
	}
	
	@Test
	void testFindAll() {
		List<FlywaySchemaHistory> mockEntityList = input.entityList();
		
		when(repository.findAll()).thenReturn(mockEntityList);
		
		List<FlywaySchemaHistory> resultList = service.findAll();
		
		FlywaySchemaHistory flywayZero = resultList.get(0);
		
		assertEquals(0, flywayZero.getInstalleRank());
		assertEquals("Version0", flywayZero.getVersion());
		assertEquals("Description0", flywayZero.getDescription());
		assertEquals("Type0", flywayZero.getType());
		assertEquals("Script0", flywayZero.getScript());
		assertEquals(0, flywayZero.getChecksum());
		assertEquals("Installed By0", flywayZero.getInstalledBy());
		assertEquals(new Date(0), flywayZero.getInstalledOn());
		assertEquals(0, flywayZero.getExecutionTime());
		assertEquals(true, flywayZero.getSuccess());
		
		FlywaySchemaHistory flywayOne = resultList.get(1);
		
		assertEquals(1, flywayOne.getInstalleRank());
		assertEquals("Version1", flywayOne.getVersion());
		assertEquals("Description1", flywayOne.getDescription());
		assertEquals("Type1", flywayOne.getType());
		assertEquals("Script1", flywayOne.getScript());
		assertEquals(1, flywayOne.getChecksum());
		assertEquals("Installed By1", flywayOne.getInstalledBy());
		assertEquals(new Date(1), flywayOne.getInstalledOn());
		assertEquals(1, flywayOne.getExecutionTime());
		assertEquals(true, flywayOne.getSuccess());
	}
	
}
