package br.com.ibpt.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.services.v1.VersionService;
import br.com.ibpt.utils.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Version", description = "Endpoints For Managing Version")
@RestController
@RequestMapping(path = "/v1/version")
public class VersionController {

	@Autowired
	private VersionService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds All Versions",
		description = "Finds All Versions",
		tags = {"Version"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = VersionVO.class)))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping
	public ResponseEntity<PagedModel<EntityModel<VersionVO>>> findCustomPaginable(
		@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
		@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
		@RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,
		@RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "effectivePeriodMonth", required = false) String effectivePeriodMonth,
		@RequestParam(value = "effectivePeriodYear", required = false) String effectivePeriodYear
	) {
		
		sortBy = "effectivePeriodUntil".equalsIgnoreCase(sortBy) ? "effectivePeriodUntil" : "name";
		
		Pageable pageable = util.pageable(page, size);
		
		return ResponseEntity.ok(
				   service.findCustomPaginable(
			           pageable,
					   name, 
					   effectivePeriodMonth,
				       effectivePeriodYear,
				       sortBy,
				       direction
			       )
			   );
	}
	
	@Operation(
		summary = "Finds a Version",
		description = "Finds a Version By Id",
		tags = {"Version"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = VersionVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/{id}")
	public VersionVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Version",
		description = "Creates a Version",
		tags = {"Version"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = VersionVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping
	public VersionVO create(@RequestBody VersionVO data) {
		return service.create(data);
	}
	
	@Operation(
		summary = "Updates a Version",
		description = "Updates a Version By Id",
		tags = {"Version"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = VersionVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/{id}")
	public VersionVO updateById(@PathVariable("id") Integer id, @RequestBody VersionVO data) {
		return service.updateById(id, data);
	}
	
	@Operation(
		summary = "Deletes a Version",
		description = "Deletes a Version By Id",
		tags = {"Version"},
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
