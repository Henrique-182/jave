package br.com.ini.proxys.v1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.data.vo.v1.FileStorageResponseVO;
import br.com.ini.data.vo.v1.FileVO;
import br.com.ini.models.v1.ProjectName;
import br.com.ini.models.v1.ServiceName;

@FeignClient(name = "file-service", url = "localhost:8686")
public interface FileProxy {

	@PostMapping(
		path = "/v1/file/upload/{service}/{project}/{identifier}/{isProcessed}", 
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public FileStorageResponseVO uploadFile(
		@PathVariable("service") ServiceName serviceName,
		@PathVariable("project") ProjectName projectName,
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@RequestPart("file") MultipartFile file
	);
	
	@PostMapping(path = "/v1/file/upload/{service}/{project}/{identifier}/{isProcessed}/{filename}")
	public FileStorageResponseVO uploadFile(
		@PathVariable("service") ServiceName serviceName,
		@PathVariable("project") ProjectName projectName,
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@PathVariable("filename") String filename,
		@RequestBody FileVO data
	);
	
	@GetMapping(path = "/v1/file/download/{service}/{project}/{identifier}/{isProcessed}/{filename}")
	public Resource getFile(
		@PathVariable("service") ServiceName serviceName,
		@PathVariable("project") ProjectName projectName,
		@PathVariable("identifier") String identifier,
		@PathVariable("isProcessed") Boolean isProcessed,
		@PathVariable("filename") String filename
	);
	
}
