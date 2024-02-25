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

@RestController
@RequestMapping(path = "/v1/file")
public class FileController {

	@Autowired
	private FileStorageService service;
	
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
