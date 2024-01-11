package br.com.sistemas.controllers.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistemas.data.vo.v1.CompanyActiveVO;
import br.com.sistemas.data.vo.v1.CompanyVO;
import br.com.sistemas.services.v1.CompanyService;
import br.com.sistemas.utils.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/v1/company")
public class CompanyController {
	
	@Autowired
	private CompanyService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds All Companies",
		description = "Finds All Companies",
		tags = {"Company"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = CompanyVO.class)))
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
	public ResponseEntity<PagedModel<EntityModel<CompanyVO>>> findPageable(
		@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
		@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
		@RequestParam(value = "sortBy", defaultValue = "tradeName", required = false) String sortBy,
		@RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,	
		@RequestParam(value = "cnpj", required = false) String cnpj,
		@RequestParam(value = "name", required = false) String name,
		@RequestParam(value = "isActive", required = false) Boolean isActive,
		@RequestParam(value = "softwareName", required = false) String softwareName,
		@RequestParam(value = "softwareType", required = false) String softwareType,
		@RequestParam(value = "softwareFkSameDb", required = false) Integer softwareFkSameDb
	) {
		sortBy = sortBy.equalsIgnoreCase("cnpj") ? "cnpj"
				: sortBy.equalsIgnoreCase("businessName") ? "businessName"
				: sortBy.equalsIgnoreCase("isActive") ? "isActive"
				: sortBy.equalsIgnoreCase("softwareName") ? "softwares.software.name"
				: sortBy.equalsIgnoreCase("softwareType") ? "softwares.type"
				: "tradeName";

		Pageable pageable = util.pageable(page, size, sortBy, direction);
		
		Map<String, Object> params = new HashMap<>();
		params.put("cnpj", cnpj);
		params.put("name", name);
		params.put("isActive", isActive);
		params.put("softwareName", softwareName);
		params.put("softwareType", softwareType);
		params.put("softwareFkSameDb", softwareFkSameDb);

		return ResponseEntity.ok(service.findPageable(pageable, params));
	}
	
	@Operation(
		summary = "Finds a Company",
		description = "Finds a Company by Id",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@GetMapping(path = "/{id}")
	public CompanyVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Company",
		description = "Creates a Company",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping
	public CompanyVO create(@RequestBody CompanyVO data) {
		return service.create(data);
	}
	
	@Operation(
		summary = "Updates a Company",
		description = "Updates a Company By Id",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/{id}")
	public CompanyVO updateById(@PathVariable("id") Integer id, @RequestBody CompanyVO data) {
		return service.updateById(id, data);
	}
	
	@Operation(
		summary = "Actives ou Inactives a Company",
		description = "Actives ou Inactives a Company By Id",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PatchMapping
	public void updateCompanyIsActiveById(@RequestBody CompanyActiveVO data) {
		service.updateCompanyIsActiveById(data);
	}
	
	@PatchMapping(path = "/software")
	public void updateCompanySoftwareIsActiveById(@RequestBody CompanyActiveVO data) {
		service.updateCompanySoftwareIsActiveById(data);
	}
	
	@Operation(
			summary = "Deletes a Company",
			description = "Deletes a Company By Id",
			tags = {"Company"},
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
