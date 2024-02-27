package br.com.ini.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.data.vo.v1.FileStorageResponseVO;
import br.com.ini.data.vo.v1.IniVO;
import br.com.ini.services.v1.IcmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Icms", description = "Endpoints for managing Icms in .ini Files")
@RestController
@RequestMapping(path = "/v1/icms")
public class IcmsController {

	@Autowired
	private IcmsService service;
	
	@Operation(
		summary = "Uploads a File",
		description = "Uploads a File by Proxy",
		tags = {"Icms"},
		responses = {
			@ApiResponse(
				description = "Success",
				responseCode = "200",
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = FileStorageResponseVO.class)))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)		
	@PostMapping(path = "/upload/{identifier}")
	public FileStorageResponseVO uploadFile(
		@RequestParam("file") MultipartFile file,
		@PathVariable("identifier") String identifier
	) {
		return service.uploadFile(file, identifier);
	}
	
	@Operation(
		summary = "Downloads a File",
		description = "Downloads a File by Proxy",
		tags = {"Icms"},
		responses = {
			@ApiResponse(
				description = "Success",
				responseCode = "200",
				content = @Content(
					mediaType = "multipart/form-data",
					schema = @Schema(implementation = Resource.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/download/{identifier}/{isProcessed}/{filename}")
	public Resource downloadFile(
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@PathVariable("filename") String filename
	) {
		return service.downloadFile(identifier, isProcessed, filename);
	}
	
	@Operation(
		summary = "File Into Json",
		description = "Transforms a .ini File into a Json Representation",
		tags = {"Icms"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = IniVO.class)))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@GetMapping(path = "/file-to-json/{identifier}/{filename}")
	public IniVO fileToJson(
		@PathVariable("identifier") String identifier, 
		@PathVariable("filename") String filename
	) {
		return service.fileToJson(identifier, filename);
	}
	
	@Operation(
		summary = "Json Into File",
		description = "Transforms a Json Representation into a .ini File",
		tags = {"Icms"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping(path = "/json-to-file")
	public ResponseEntity<?> jsonToFile(
		@RequestBody IniVO data
	) {
		service.jsonToFile(data);
		
		return ResponseEntity.noContent().build();
	}
	
}
