package br.com.jave.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jave.data.vo.v1.AccountCredentialsVO;
import br.com.jave.services.v1.AuthService;

@RestController
@RequestMapping(path = "/v1/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@PostMapping(path = "/signin")
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request. Data is null or blank");
		
		var token = service.signin(data);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request. Token is Null!");
		
		return token;
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null 
			|| data.getUsername() == null 
			|| data.getUsername().isBlank()
			|| data.getPassword() == null
			|| data.getPassword().isBlank();
	}
	
}
