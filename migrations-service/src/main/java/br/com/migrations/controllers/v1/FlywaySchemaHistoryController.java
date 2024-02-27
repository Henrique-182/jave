package br.com.migrations.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.migrations.model.v1.FlywaySchemaHistory;
import br.com.migrations.services.v1.FlywaySchemaHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Flyway", description = "Endpoints for Managing Flyway Schema History")
@RestController
@RequestMapping(path = "/v1/flyway/migration")
public class FlywaySchemaHistoryController {

	@Autowired
	private FlywaySchemaHistoryService service;
	
	@Operation(
		summary = "Finds All Migrations",
		description = "Finds All Migrations",
		tags = {"Flyway"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping
	public List<FlywaySchemaHistory> findAll() {
		return service.findAll();
	}
	
}
