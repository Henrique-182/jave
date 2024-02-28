package br.com.conhecimento.controllers.v1;

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

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.services.v1.TopicService;
import br.com.conhecimento.utils.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Topic", description = "Endpoints for Managing Topics")
@RestController
@RequestMapping(path = "/v1/topic")
public class TopicController {
	
	@Autowired
	private TopicService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds All Topics",
		description = "Finds All Topics",
		tags = {"Topic"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = TopicVO.class)))
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
	public PagedModel<EntityModel<TopicVO>> findCustomPageable(
		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "name") String sortBy,
		@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
		@RequestParam(name = "topicName", required = false, defaultValue = "") String topicName
	) {
		
		Pageable pageable = util.pageable(page, size, sortBy, direction);
		
		return service.findCustomPageable(topicName, pageable);
	}
	
	@Operation(
		summary = "Finds a Topic",
		description = "Finds a Topic By Id",
		tags = {"Topic"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TopicVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/{id}")
	public TopicVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Topic",
		description = "Creates a Topic",
		tags = {"Topic"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = TopicVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping
	public TopicVO create(@RequestBody TopicVO data) {
		return service.create(data);
	}
	
	@Operation(
		summary = "Updates a Topic",
		description = "Updates a Topic",
		tags = {"Topic"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = TopicVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/{id}")
	public TopicVO updateById(@PathVariable("id") Integer id, @RequestBody TopicVO data) {
		return service.updateById(id, data);
	}
	
	@Operation(
		summary = "Deletes a Topic",
		description = "Deletes a Topic",
		tags = {"Topic"},
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "204", content = @Content(schema = @Schema(implementation = TopicVO.class))),
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
