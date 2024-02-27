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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Email", description = "Endpoints for Managing Email")
@RestController
@RequestMapping("/v1/email")
public class EmailController {

	@Autowired
	private EmailService service;
	
	@Operation(
		summary = "Sends an Email",
		description = "Sends an Email",
		tags = {"Email"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "201", content = @Content(schema = @Schema(implementation = Email.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping(path = "/send")
	public ResponseEntity<Email> send(
		@RequestBody @Valid EmailVO data, 
		@RequestParam(name = "isHtml", required = false, defaultValue = "false") Boolean isHtml
	) {
		
		Email email = service.send(data, isHtml);
		
		return new ResponseEntity<>(email, HttpStatus.CREATED);
	}
	
}
