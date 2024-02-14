package br.com.ibpt.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.services.v1.EmailService;
import jakarta.validation.constraints.Email;

@RestController
@RequestMapping(path = "/v1/email")
public class EmailController {
	
	@Autowired
	private EmailService service;
	
	@PostMapping(path = "/version/all")
	public void sendAllVersions(
		@RequestParam(name = "to", required = true, defaultValue = "") @Email String emailTo,
		@RequestParam(name = "isHtml", required = true, defaultValue = "false") Boolean isHtml
	) {
		service.sendAllVersions(emailTo, isHtml);
	}
	
	@PostMapping(path = "/ibpt-by-version")
	public void sendAllIbpts(
			@RequestParam(name = "to", required = true, defaultValue = "") @Email String emailTo,
			@RequestParam(name = "isHtml", required = true, defaultValue = "false") Boolean isHtml,
			@RequestParam(name = "versionName", required = true) String versionName
			) {
		service.sendAllIbpts(emailTo, isHtml, versionName);
	}

}
