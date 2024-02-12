package br.com.sistemas.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.sistemas.services.v1.EmailService;
import jakarta.validation.constraints.Email;

@RestController
@RequestMapping(path = "/v1/email")
public class EmailController {

	@Autowired
	private EmailService service;
	
	@PostMapping(path = "/company/all")
	public void sendAllCompanies(
		@RequestParam(name = "to", required = true, defaultValue = "") @Email String emailTo,
		@RequestParam(name = "isHtml", required = true, defaultValue = "false") Boolean isHtml
	) {
		service.sendAllCompanies(emailTo, isHtml);
	}
	
}
