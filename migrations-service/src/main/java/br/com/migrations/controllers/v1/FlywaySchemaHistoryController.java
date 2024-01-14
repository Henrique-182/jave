package br.com.migrations.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.migrations.model.v1.FlywaySchemaHistory;
import br.com.migrations.services.v1.FlywaySchemaHistoryService;

@RestController
@RequestMapping(path = "/v1/flyway/migrations")
public class FlywaySchemaHistoryController {

	@Autowired
	private FlywaySchemaHistoryService service;
	
	@GetMapping
	public List<FlywaySchemaHistory> findAll() {
		return service.findAll();
	}
	
}
