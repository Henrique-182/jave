package br.com.conhecimento.controllers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.conhecimento.data.vo.v2.KnowledgeVO;
import br.com.conhecimento.model.v2.UserAudit;
import br.com.conhecimento.services.v2.KnowledgeService;
import br.com.conhecimento.services.v2.UserAuditService;
import br.com.conhecimento.utils.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Knowledge", description = "Endpoints for Managing Knowledges")
@RestController
@RequestMapping(path = "/v1/knowledge")
public class KnowledgeController {

	@Autowired
	private KnowledgeService service;
	
	@Autowired
	private UserAuditService userAuditService;
	
	@Operation(
		summary = "Finds All Knowledges",
		description = "Finds All Knowledges",
		tags = {"Knowledge"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = KnowledgeVO.class)))
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
	public PagedModel<EntityModel<KnowledgeVO>> findCustomPageable(
		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "title") String sortBy,
		@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
		@RequestParam(name = "knowledgeTitle", required = false, defaultValue = "") String knowledgeTitle,
		@RequestParam(name = "knowledgeDescription", required = false, defaultValue = "") String knowledgeDescription,
		@RequestParam(name = "knowledgeContent", required = false, defaultValue = "") String knowledgeContent
	) {
		Pageable pageable = ControllerUtil.pageable(page, size, sortBy, direction);
		
		return service.findCustomPageable(knowledgeTitle, knowledgeDescription, knowledgeContent, pageable);
	}
	
	@Operation(
		summary = "Finds a Knowledge",
		description = "Finds a Knowledge by Id",
		tags = {"Knowledge"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = KnowledgeVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@GetMapping(path = "/{id}")
	public KnowledgeVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Knowledge",
		description = "Creates a Knowledge",
		tags = {"Knowledge"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = KnowledgeVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping
	public KnowledgeVO create(@RequestBody KnowledgeVO data) {
		UserAudit userAudit = userAuditService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		return service.create(data, userAudit);
	}
	

	@Operation(
		summary = "Updates a Knowledge",
		description = "Updates a Knowledge",
		tags = {"Knowledge"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = KnowledgeVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/{id}")
	public KnowledgeVO updateById(@PathVariable("id") Integer id, @RequestBody KnowledgeVO data) {
		UserAudit userAudit = userAuditService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		return service.updateById(id, data, userAudit);
	}
	
	@Operation(
		summary = "Deletes a Knowledge",
		description = "Deletes a Knowledge",
		tags = {"Knowledge"},
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
