package br.com.ibpt.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.AccountCredentialsVO;
import br.com.ibpt.data.vo.v1.TokenVO;
import br.com.ibpt.services.v1.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoint for Managing Authentication")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@SuppressWarnings("rawtypes")
	@Operation(
		summary = "Authenticates a user",
		description = "Authenticates a user and returns a token",
		tags = {"Auth"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenVO.class))),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@PostMapping("/signin")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		
		var token = authService.signin(data);
		
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		
		return token;
	}
	
	@SuppressWarnings("rawtypes")
	@Operation(
		summary = "Refresh token",
		description = "Refresh token for authenticated user and returns a token",
		tags = {"Auth"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenVO.class))),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@PutMapping("/refresh/{userName}")
	public ResponseEntity refreshToken(
		@PathVariable("userName") String userName,
		@RequestHeader("Authorization") String refreshToken
	) {
		if (checkIsParamsIsNotNull(userName, refreshToken)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		
		var token = authService.refreshToken(userName, refreshToken);
		
		if (token == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		}
		
		return token;
	}

	private boolean checkIsParamsIsNotNull(String userName, String refreshToken) {
		return refreshToken == null 
			|| refreshToken.isBlank()
			|| userName == null
			|| userName.isBlank();
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null 
			|| data.getUserName() == null 
			|| data.getUserName().isBlank() 
			|| data.getPassword() == null 
			|| data.getPassword().isBlank();
	}
}
