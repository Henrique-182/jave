package br.com.jave.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jave.data.vo.v1.AccountCredentialsVO;
import br.com.jave.data.vo.v1.TokenVO;
import br.com.jave.services.v1.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Auth", description = "Endpoints for Managing Auth")
@RestController
@RequestMapping(path = "/v1/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@Operation(
		summary = "Authenticates a User",
		description = "Authenticates a User and returns a token",
		tags = "Auth",
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenVO.class))),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping(path = "/signin")
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request. Data is null or blank");
		
		var token = service.signin(data);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request. Token is Null!");
		
		return token;
	}
	
	@Operation(
		summary = "Refresh Token",
		description = "Refresh Token for Authenticated User and Returns a Token",
		tags = "Auth",
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenVO.class))),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/refresh/{username}")
	public ResponseEntity<?> refresh(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid cliente request!");
		
		var token = service.refresh(username, refreshToken);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		return token;
	}

	@Operation(
		summary = "Validates a Token",
		description = "Validates a Token",
		tags = "Auth",
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/validate")
	public ResponseEntity<?> validate(HttpServletRequest request) {
		return service.validate(request) 
				? ResponseEntity.status(200).body("Valid Token!")
				: ResponseEntity.status(401).body("Invalid Token!");
	}
	
	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return username == null 
			|| username.isBlank()
			|| refreshToken.isBlank()
			|| refreshToken == null;
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null 
			|| data.getUsername() == null 
			|| data.getUsername().isBlank()
			|| data.getPassword() == null
			|| data.getPassword().isBlank();
	}
	
}
