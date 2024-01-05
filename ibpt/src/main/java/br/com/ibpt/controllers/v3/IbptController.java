package br.com.ibpt.controllers.v3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v2.IbptUpdateVO;
import br.com.ibpt.data.vo.v3.IbptVO;
import br.com.ibpt.model.v3.UserAudit;
import br.com.ibpt.services.v3.IbptService;
import br.com.ibpt.util.v3.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v3/ibpt")
@Tag(name = "Ibpt", description = "Endpoints for Managing Ibpt")
public class IbptController {

	@Autowired
	private IbptService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds All Ibpt",
		description = "Finds All Ibpt",
		tags = {"Ibpt"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = IbptVO.class)))
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
	public ResponseEntity<PagedModel<EntityModel<IbptVO>>> findCustomPageable(
		@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
		@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
		@RequestParam(value = "direction", defaultValue = "asc", required = false) String direction,	
		@RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
		@RequestParam(value = "id", required = false) Integer id,
		@RequestParam(value = "versionName", required = false) String versionName,
		@RequestParam(value = "companyCnpj", required = false) String companyCnpj,
		@RequestParam(value = "companyName", required = false) String companyName,
		@RequestParam(value = "isUpdated", required = false) Boolean isUpdated
	) {
		sortBy = sortBy.equalsIgnoreCase("companyCnpj") ? "companySoftware.company.cnpj"
				: sortBy.equalsIgnoreCase("companyName") ? "companySoftware.company.tradeName"
				: sortBy.equalsIgnoreCase("isUpdated") ? "isUpdated"
				: sortBy.equalsIgnoreCase("versionName") ? "version.name"
				: "id";
		
		Pageable pageable = util.pageable(page, size);
	
		return ResponseEntity.ok(
					service.findCustomPaginable(
						pageable, 
						id, 
						versionName,
						companyCnpj,
						companyName,
						isUpdated,
						sortBy,
						direction
					)
				);
	}
	
	@GetMapping(path = "/{id}")
	public IbptVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Ibpt",
		description = "Creates a Ibpt By Version Id",
		tags = {"Ibpt"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@PostMapping(path = "/{versionId}")
	public ResponseEntity<?> create(@PathVariable("versionId") Integer versionId) {
		service.callProcNewIbpt(versionId);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(
		summary = "Updates a Ibpt",
		description = "Updates a Ibpt By Id",
		tags = {"Ibpt"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@PatchMapping
	public ResponseEntity<?> updateById(@RequestBody IbptUpdateVO data) {
		UserAudit userAudit = util.findUserByContext(SecurityContextHolder.getContext());
		
		service.updateById(data, userAudit);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(
		summary = "Deletes a Ibpt",
		description = "Deletes a Ibpt By Id",
		tags = {"Ibpt"},
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
