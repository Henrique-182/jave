package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.CompanyVO;
import br.com.ibpt.services.v1.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v1/empresa")
@Tag(name = "Company", description = "Endpoints for Managing Companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	@GetMapping(path = "/{id}")
	@Operation(
		summary = "Finds a Company",
		description = "Finds a Company By Id",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public CompanyVO findById(@PathVariable("id") Integer id) {
		return companyService.findById(id);
	}
	
	@GetMapping
	@Operation(
		summary = "Finds all Companies",
		description = "Finds all Companies",
		tags = {"Company"},
		responses = {
			@ApiResponse(
				description = "Success",
				responseCode = "200",
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = CompanyVO.class))
				)
			),
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public List<CompanyVO> findCustom(
		@RequestParam(value = "cnpj", required = false) String cnpj,
		@RequestParam(value = "nome", required = false) String tradeName,
		@RequestParam(value = "razaoSocial", required = false) String businessName,
		@RequestParam(value = "sistema", required = false) String software,
		@RequestParam(value = "conexao", required = false) String connection,
		@RequestParam(value = "temAutorizacao", required = false) Boolean haveAuthorization,
		@RequestParam(value = "estaAtivo", required = false) Boolean isActive,
		@RequestParam(value = "fkEmpresaMesmoBd", required = false) Integer fkCompanySameDb
	) {
		return companyService.findCustom(cnpj, tradeName, businessName, software, connection, haveAuthorization, isActive, fkCompanySameDb);
	}
	
	@PostMapping(
		path = "/novo",
		consumes = {MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@Operation(
		summary = "Adds a Company",
		description = "Adds a Company",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public CompanyVO create(@RequestBody CompanyVO vo) {
		return companyService.create(vo);
	}
	
	@PutMapping(path = "/{id}")
	@Operation(
		summary = "Updates a Company",
		description = "Updates a Company",
		tags = {"Company"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = CompanyVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public CompanyVO updateById(@RequestBody CompanyVO vo, @PathVariable("id") Integer id) {
		return companyService.updateById(vo, id);
	}
	
}
