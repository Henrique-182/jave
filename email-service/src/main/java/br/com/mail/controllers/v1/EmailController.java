package br.com.mail.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.mail.data.vo.v1.EmailVO;
import br.com.mail.model.v1.Email;
import br.com.mail.services.v1.EmailService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/email")
public class EmailController {

	@Autowired
	private EmailService service;
	
	@PostMapping(path = "/send")
	public ResponseEntity<Email> send(
		@RequestBody @Valid EmailVO data, 
		@RequestParam(name = "isHtml", required = false, defaultValue = "false") Boolean isHtml
	) {
		
		Email email = service.send(data, isHtml);
		
		return new ResponseEntity<>(email, HttpStatus.CREATED);
	}
	
}
