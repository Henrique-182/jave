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

@RestController
@RequestMapping(path = "/v1/icms")
public class IcmsController {

	@Autowired
	private IcmsService service;
	
	@PostMapping(path = "/upload/{identifier}")
	public FileStorageResponseVO uploadFile(
		@RequestParam("file") MultipartFile file,
		@PathVariable("identifier") String identifier
	) {
		return service.uploadFile(file, identifier);
	}
	
	@GetMapping(path = "/download/{identifier}/{isProcessed}/{filename}")
	public Resource downloadFile(
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@PathVariable("filename") String filename
	) {
		return service.downloadFile(identifier, isProcessed, filename);
	}
	
	@GetMapping(path = "/file-to-json/{identifier}/{filename}")
	public IniVO fileToJson(
		@PathVariable("identifier") String identifier, 
		@PathVariable("filename") String filename
	) {
		return service.fileToJson(identifier, filename);
	}
	
	@PostMapping(path = "/json-to-file")
	public ResponseEntity<?> jsonToFile(
		@RequestBody IniVO data
	) {
		service.jsonToFile(data);
		
		return ResponseEntity.noContent().build();
	}
	
}
