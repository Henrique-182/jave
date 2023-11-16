package br.com.ibpt.controllers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v2.IbptNewVO;
import br.com.ibpt.data.vo.v2.IbptUpdateVO;
import br.com.ibpt.data.vo.v2.IbptVO;
import br.com.ibpt.services.v2.IbptService;
import br.com.ibpt.util.v2.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v2/atualizacao")
@Tag(name = "Ibpt", description = "Endpoints for Managing Ibpt")
public class IbptController {

	@Autowired
	private IbptService service;
	
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
	public ResponseEntity<PagedModel<EntityModel<IbptVO>>> findAll(
		@RequestParam(value = "pagina", defaultValue = "0", required = false) Integer page,
		@RequestParam(value = "tamanho", defaultValue = "10", required = false) Integer size,
		@RequestParam(value = "direcao", defaultValue = "asc", required = false) String direction,	
		@RequestParam(value = "ordenadoPor", defaultValue = "id", required = false) String sortBy,
		@RequestParam(value = "id", required = false) Integer id,
		@RequestParam(value = "versaoNome", required = false) String versionName,
		@RequestParam(value = "empresaCnpj", required = false) String companyCnpj,
		@RequestParam(value = "empresaNome", required = false) String companyName,
		@RequestParam(value = "estaAtualizado", required = false) Boolean isUpdated
	) {
		
		Pageable pageable = ControllerUtil.pageable(page, size, direction, sortBy);
	
		return ResponseEntity.ok(
					service.findCustomPaginable(
						pageable, 
						id, 
						versionName,
						companyCnpj,
						companyName,
						isUpdated
					)
				);
	}
	
	@Operation(
		summary = "Creates a Ibpt",
		description = "Creates a Ibpt By Version Id",
		tags = {"Ibpt"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@PostMapping
	public void create(@RequestBody IbptNewVO data) {
		service.callProcNewIbpt(data);
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
	public void updateById(@RequestBody IbptUpdateVO data) {
		service.updateById(data);
	}
}
