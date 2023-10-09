package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.IbptUpdateVO;
import br.com.ibpt.data.vo.v1.UpdateVO;
import br.com.ibpt.services.v1.IbptUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/v1/atualizacao")
@Tag(name = "IBPT Update", description = "Endpoints for Managing IBPT Update")
public class IbptUpdateController {

	@Autowired
	private IbptUpdateService ibptUpdateService;
	
	@GetMapping
	@Operation(
		summary = "Finds a Update",
		description = "Finds a Updated Custom",
		tags = {"IBPT Update"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = IbptUpdateVO.class))
				)
			),
			@ApiResponse(description = "No content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
		}
	)
	public List<IbptUpdateVO> findCustom(
		@RequestParam(value = "versaoNome", required = false) String versionName,
		@RequestParam(value = "empresaCnpj", required = false) String companyCnpj,
		@RequestParam(value = "empresaNome", required = false) String companyBusinessName,
		@RequestParam(value = "empresaRazao", required = false) String companyTradeName,
		@RequestParam(value = "estaAtualizado", required = false) Boolean isUpdated
	) {
		return ibptUpdateService.findCustom(versionName, companyCnpj, companyBusinessName, companyTradeName, isUpdated);
	}
	
	@PutMapping
	@Operation(
			summary = "Updates a Update",
			description = "Updates a Updated Custom",
			tags = {"IBPT Update"},
			responses = {
				@ApiResponse(description = "Success", responseCode = "200", content = @Content),
				@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
				@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
				@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
			}
		)
	public void updateById(@RequestBody UpdateVO vo) {
		ibptUpdateService.updateById(vo.getId(), vo.getValue());
	}
	
}
