package br.com.ibpt.controllers.v2;

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

import br.com.ibpt.data.vo.v2.CompanyActiveVO;
import br.com.ibpt.data.vo.v2.CompanyVO;
import br.com.ibpt.services.v2.CompanyService;
import br.com.ibpt.util.v2.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v2/empresa")
@Tag(name = "Company", description = "Endpoints For Managing Companies")
public class CompanyController {

	@Autowired
	private CompanyService service;
	
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
	public ResponseEntity<PagedModel<EntityModel<CompanyVO>>> findAll(
		@RequestParam(value = "pagina", defaultValue = "0", required = false) Integer page,
		@RequestParam(value = "tamanho", defaultValue = "10", required = false) Integer size,
		@RequestParam(value = "direcao", defaultValue = "asc", required = false) String direction,	
		@RequestParam(value = "ordenadoPor", defaultValue = "nome", required = false) String sortBy
	) {
		sortBy = sortBy.equalsIgnoreCase("cnpj") ? "cnpj"
				: sortBy.equalsIgnoreCase("razaoSocial") ? "businessName"
				: sortBy.equalsIgnoreCase("observacao") ? "observation"
				: sortBy.equalsIgnoreCase("estaAtivo") ? "isActive"
				: "tradeName";
		
		Pageable pageable = ControllerUtil.pageable(page, size, direction, sortBy);
		
		return ResponseEntity.ok(service.findAll(pageable));
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
	
	@PatchMapping(path = "/sistema")
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
