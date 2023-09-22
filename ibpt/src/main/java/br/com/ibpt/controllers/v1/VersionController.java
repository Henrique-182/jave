package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/v1/versao")
@Tag(name = "Version", description = "Endpoints for Managing Versions")
public class VersionController {
	
	@Autowired
	private VersionService versionService; 
	
	@GetMapping
	@Operation(
		summary = "Finds a Version",
		description = "Finds a Version by name or effectivePeriodMonth or effectivePeriodYear",
		tags = {"Version"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200",
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = VersionVO.class))
				)
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public List<VersionVO> findCustom(
			@RequestParam(value = "nome", required = false) String name,
			@RequestParam(value = "mesVigencia", required = false) String effectivePeriodMonth,
			@RequestParam(value = "anoVigencia", required = false) String effectivePeriodYear
		) {
		return versionService.findCustom(name, effectivePeriodMonth, effectivePeriodYear);
	}
	
	@PostMapping(path = "/novo")
	@Operation(
		summary = "Adds a Version",
		description = "Adds a Version",
		tags = {"Version"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = VersionVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public VersionVO create(@RequestBody VersionVO vo) {
		return versionService.create(vo);
	}
	
	@PutMapping(path = "/{id}")
	@Operation(
		summary = "Updates a Version",
		description = "Updates a Version",
		tags = {"Version"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = VersionVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	public VersionVO update(@RequestBody VersionVO vo, @PathVariable("id") Integer id) {
		return versionService.update(vo, id);
	}
}
