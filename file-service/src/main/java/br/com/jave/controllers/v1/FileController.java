package br.com.jave.controllers.v1;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.jave.data.vo.v1.FileStorageResponseVO;
import br.com.jave.data.vo.v1.FileVO;
import br.com.jave.models.v1.ProjectName;
import br.com.jave.models.v1.ServiceName;
import br.com.jave.services.v1.FileStorageService;
import br.com.jave.utils.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "File", description = "Endpoints for Managing Files")
@RestController
@RequestMapping(path = "/v1/file")
public class FileController {

	@Autowired
	private FileStorageService service;
	
	@Operation(
		summary = "Uploads a File",
		description = "Uploads a File using MultipartFile",
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
	@PostMapping(path = "/upload/{service}/{project}/{identifier}/{isProcessed}")
	public FileStorageResponseVO uploadFile(
		@PathVariable("service") ServiceName serviceName,
		@PathVariable("project") ProjectName projectName,
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@RequestParam("file") MultipartFile file
	) {
		String folder = ControllerUtil.concatFolder(serviceName, projectName, identifier, isProcessed);
		
		String filename = service.storeFile(folder, file);
		
		String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/v1/file/download/")
				.path(folder + "/")
				.path(filename)
				.toUriString();
		
		return new FileStorageResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@Operation(
		summary = "Uploads a File",
		description = "Uploads a File using FileVO ",
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
	@PostMapping(path = "/upload/{service}/{project}/{identifier}/{isProcessed}/{filename}")
	public FileStorageResponseVO uploadFile(
			@PathVariable("service") ServiceName serviceName,
			@PathVariable("project") ProjectName projectName,
			@PathVariable("identifier") String identifier,
			@PathVariable("isProcessed") Boolean isProcessed,
			@PathVariable("filename") String filename,
			@RequestBody FileVO data
	) {
		String folder = ControllerUtil.concatFolder(serviceName, projectName, identifier, isProcessed);
		
		File file = service.storeFile(folder, filename, data);
		
		String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/v1/file/download/")
				.path(folder + "/")
				.path(filename)
				.toUriString();
		
		return new FileStorageResponseVO(filename, fileDownloadUri, "multipart/form-data", file.length());
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
	@GetMapping(path = "/download/{service}/{project}/{identifier}/{isProcessed}/{filename}")
	public Resource downloadFile(
		@PathVariable("service") ServiceName serviceName,
		@PathVariable("project") ProjectName projectName,
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@PathVariable("filename") String filename
	) {
		String folder = ControllerUtil.concatFolder(serviceName, projectName, identifier, isProcessed);
		
		return service.loadFileAsResource(folder, filename);
	}
	
}
