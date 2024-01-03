package br.com.conhecimento.controllers.v2;

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

import br.com.conhecimento.data.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.data.vo.v2.UserVO;
import br.com.conhecimento.services.v2.UserService;
import br.com.conhecimento.utils.v2.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Endpoints for Managing Users")
@RestController
@RequestMapping(path = "/v2/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds All Users",
		description = "Finds All Users",
		tags = "User",
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = UserVO.class))
				)
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@GetMapping
	public PagedModel<EntityModel<UserVO>> findCustomPageable(
		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
		@RequestParam(name = "sortBy", required = false, defaultValue = "username") String sortBy,
		@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
		@RequestParam(name = "username", required = false, defaultValue = "") String username,
		@RequestParam(name = "permissionDescription", required = false, defaultValue = "") String permissionDescription
	) {
		
		Pageable pageble = util.pageable(page, size, sortBy, direction);
		
		return service.findCustomPageable(username, permissionDescription, pageble);
	}
	
	@Operation(
		summary = "Finds a User",
		description = "Finds a User By Id",
		tags = "User",
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = UserVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@GetMapping(path = "/{id}")
	public UserVO findById(@PathVariable("id") Integer id) {
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a User",
		description = "Creates a User",
		tags = "User",
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = UserVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@PostMapping(path = "/new")
	public UserVO create(@RequestBody AccountCredentialsVO data) {
		return service.create(data);
	}
	
	
	@Operation(
		summary = "Updates a User",
		description = "Finds a User By Id",
		tags = "User",
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = UserVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@PutMapping(path = "/{id}")
	public UserVO updateById(@PathVariable("id") Integer id, @RequestBody UserVO data) {
		return service.updateById(id, data);
	}
	
	@Operation(
		summary = "Deletes a User",
		description = "Deletes a User By Id",
		tags = "User",
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
