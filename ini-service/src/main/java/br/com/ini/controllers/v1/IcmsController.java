package br.com.ini.controllers.v1;

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

import br.com.ini.data.vo.v1.FileStorageResponseVO;
import br.com.ini.data.vo.v1.IniVO;
import br.com.ini.services.v1.IcmsService;
import br.com.ini.utils.v1.ControllerUtil;

@RestController
@RequestMapping(path = "/v1/icms")
public class IcmsController {

	@Autowired
	private IcmsService service;
	
	@PostMapping(path = "/upload/{cnpj}")
	public FileStorageResponseVO uploadFile(@PathVariable("cnpj") String cnpj, @RequestParam("file") MultipartFile file) {
		String folder = ControllerUtil.concatFolder("icms", cnpj);
		
		String filename = service.storeFile(folder, file);
		
		String fileDownloadUri = ServletUriComponentsBuilder
				.fromCurrentContextPath()
				.path("/v1/icms/download/")
				.path(cnpj + "/")
				.path(filename)
				.toUriString();
		
		return new FileStorageResponseVO(filename, fileDownloadUri, file.getContentType(), file.getSize());
	}
	
	@GetMapping(path = "/download/{cnpj}/{filename}")
	public Resource downloadFile(
		@PathVariable("cnpj") String cnpj,
		@PathVariable("filename") String filename,
		@RequestParam(value = "processed", required = false, defaultValue = "false") Boolean processed
	) {
		String folder = ControllerUtil.concatFolder("icms", cnpj, processed);
		
		return service.downloadFile(folder, filename);
	}
	
	@GetMapping(path = "/file-to-json/{cnpj}/{filename}")
	public IniVO fileToJson(@PathVariable("cnpj") String cnpj, @PathVariable("filename") String filename) {
		String folder = ControllerUtil.concatFolder("icms", cnpj);
		
		IniVO data = service.fileToJson(folder, filename);
		data.setCnpj(cnpj);
		data.setFilename(filename);
		
		return data;
	}
	
	@PostMapping(path = "/json-to-file")
	public void jsonToFile(@RequestBody IniVO data) {
		String folder = ControllerUtil.concatFolder("icms", data.getCnpj());
		
		service.jsonToFile(folder, data);
	}
	
}
