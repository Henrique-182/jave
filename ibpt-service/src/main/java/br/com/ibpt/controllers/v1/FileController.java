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

@RestController
@RequestMapping(path = "/v1/file")
public class FileController {
	
	@Autowired
	private FileService service;
	
	@PostMapping(path = "/upload/{identifier}")
	public FileStorageResponseVO uploadFile(
		@PathVariable("identifier") String identifier, 
		@RequestParam("file") MultipartFile file
	) {
		return service.uploadFile(identifier, file);
	}
	
	@GetMapping(path = "/download/{identifier}/{filename}")
	public Resource downloadFile(
		@PathVariable("identifier") String identifier,
		@PathVariable("filename") String filename
	) {
		return service.downloadFile(identifier, filename);
	}

}
