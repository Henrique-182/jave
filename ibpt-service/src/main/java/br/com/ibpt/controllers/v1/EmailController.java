package br.com.ibpt.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.services.v1.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;

@Tag(name = "Email", description = "Endpoints for Managing Email")
@RestController
@RequestMapping(path = "/v1/email")
public class EmailController {
	
	@Autowired
	private EmailService service;
	
	@Operation(
		summary = "Send All Versions",
		description = "Send All Versions by Email",
		tags = {"Email"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@PostMapping(path = "/version/all")
	public ResponseEntity<?> sendAllVersions(
		@RequestParam(name = "to", required = true, defaultValue = "") @Email String emailTo,
		@RequestParam(name = "isHtml", required = true, defaultValue = "false") Boolean isHtml
	) {
		service.sendAllVersions(emailTo, isHtml);
		
		return ResponseEntity.noContent().build();
	}
	
	@Operation(
		summary = "Send Ibpts by Version",
		description = "Send Ibpts by Version by Email",
		tags = {"Email"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping(path = "/ibpt-by-version")
	public ResponseEntity<?> sendAllIbptsbyVersion(
			@RequestParam(name = "to", required = true, defaultValue = "") @Email String emailTo,
			@RequestParam(name = "isHtml", required = true, defaultValue = "false") Boolean isHtml,
			@RequestParam(name = "versionName", required = true) String versionName
			) {
		service.sendAllIbptsbyVersion(emailTo, isHtml, versionName);

		return ResponseEntity.noContent().build();
	}

}
