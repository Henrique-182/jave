package br.com.ibpt.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ibpt.data.vo.v1.FileStorageResponseVO;
import br.com.ibpt.services.v1.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File", description = "Endpoints for Managing File")
@RestController
@RequestMapping(path = "/v1/file")
public class FileController {
	
	@Autowired
	private FileService service;
	
	@Operation(
		summary = "Uploads a File",
		description = "Uploads a File",
		tags = {"File"},
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
		@PathVariable("identifier") String identifier, 
		@RequestParam("file") MultipartFile file
	) {
		return service.uploadFile(identifier, file);
	}
	
	@Operation(
		summary = "Downloads a File",
		description = "Downloads a File",
		tags = {"File"},
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
	@GetMapping(path = "/download/{identifier}/{filename}")
	public Resource downloadFile(
		@PathVariable("identifier") String identifier,
		@PathVariable("filename") String filename
	) {
		return service.downloadFile(identifier, filename);
	}

}
