package br.com.ibpt.controllers.v3;

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

import br.com.ibpt.data.vo.v3.SoftwareVO;
import br.com.ibpt.services.v3.SoftwareService;
import br.com.ibpt.util.v2.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v3/software")
@Tag(name = "Software", description = "Endpoints for Managing Softwares")
public class SoftwareController {

	@Autowired
	private SoftwareService service;
	
	@Operation(
		summary = "Finds All Softwares",
		description = "Finds All Softwares Paginable",
		tags = {"Software"},
		responses = {
			@ApiResponse(
				description = "Success",
				responseCode = "200",
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = SoftwareVO.class))
				)
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		}
	)
	@GetMapping
	public ResponseEntity<PagedModel<EntityModel<SoftwareVO>>> findAllPageable(
		@RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "size", defaultValue = "10") Integer size,
		@RequestParam(value = "direction", defaultValue = "asc") String direction
	) {
		
		Pageable pageable = ControllerUtil.pageable(page, size, direction, "name");
		
		return ResponseEntity.ok(service.findAllPageable(pageable));
	}
	
	@Operation(
		summary = "Finds a Software",
		description = "Finds a Software By Id",
		tags = {"Software"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = SoftwareVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		}
	)
	@GetMapping(path = "/{id}")
	public SoftwareVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Software",
		description = "Creates a Software By Id",
		tags = {"Software"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = SoftwareVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		}
	)
	@PostMapping
	public SoftwareVO create(@RequestBody SoftwareVO data) {
		return service.create(data);
	}
	
	@Operation(
		summary = "Updates a Software",
		description = "Updates a Software By Id",
		tags = {"Software"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = SoftwareVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		}
	)
	@PutMapping(path = "/{id}")
	public SoftwareVO updateById(@PathVariable("id") Integer id, @RequestBody SoftwareVO data) {
		return service.updateById(id, data);
	}
	
	@Operation(
			summary = "Deletes a Software",
			description = "Deletes a Software By Id",
			tags = {"Software"},
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
