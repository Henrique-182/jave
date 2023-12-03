package br.com.ibpt.controllers.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.ibpt.data.vo.v2.FileStorageResponseVO;
import br.com.ibpt.services.v2.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/v2/file")
public class FileController {

	@Autowired
	private FileStorageService service;
	
	@PostMapping(path = "/upload/{versionName:.+.+}")
	public FileStorageResponseVO uploadFile(@PathVariable("versionName") String versionName, @RequestParam("file") MultipartFile file) {
		var filename = service.storeFile(versionName, file);
		
		String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/v2/file/download/")
				.path(versionName + "/")
				.path(filename)
				.toUriString();
		
		return new FileStorageResponseVO(file.getOriginalFilename(), fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@GetMapping(path = "/download/{versionName:.+.+}/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("versionName") String versionName, @PathVariable ("filename") String filename, HttpServletRequest request) {

		Resource resource = service.loadFileAsResource(versionName, filename);
		
		String contentType = "";
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			
		}
		
		if (contentType.isBlank()) contentType = "application/octet-stream";
		
		return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
}
