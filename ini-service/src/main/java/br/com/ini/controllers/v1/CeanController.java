package br.com.ini.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.data.vo.v1.FileStorageResponseVO;
import br.com.ini.services.v1.CeanService;

@RestController
@RequestMapping(path = "/v1/cean")
public class CeanController {

	@Autowired
	private CeanService service;
	
	@PostMapping(path = "/upload/{identifier}")
	public FileStorageResponseVO uploadFile(
		@PathVariable("identifier") String identifier, 
		@RequestParam("file") MultipartFile file
	) {
		return service.uploadFile(identifier, file);
	}
	
	@GetMapping(path = "/download/{identifier}/{isProcessed}/{filename}")
	public Resource downloadFile(
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@PathVariable("filename") String filename
	) {
		return service.downloadFile(identifier, isProcessed, filename);
	}
	
	@PostMapping(path = "/correct/{identifier}/{filename}")
	public ResponseEntity<?> correctCean(
		@PathVariable("identifier") String identifier, 
		@PathVariable("filename") String filename
	) {
		service.correctCean(identifier, filename);
		
		return ResponseEntity.noContent().build();
	}
	
}
