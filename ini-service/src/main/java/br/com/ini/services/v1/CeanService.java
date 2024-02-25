package br.com.ini.services.v1;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.data.vo.v1.FileStorageResponseVO;
import br.com.ini.data.vo.v1.FileVO;
import br.com.ini.exceptions.v1.FileStorageException;
import br.com.ini.models.v1.ProjectName;
import br.com.ini.models.v1.ServiceName;
import br.com.ini.proxys.v1.FileProxy;
import br.com.ini.utils.v1.ServiceUtil;

@Service
public class CeanService {
	
	@Autowired
	private FileProxy proxy;

	public FileStorageResponseVO uploadFile(String identifier, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		FileStorageResponseVO data = proxy.uploadFile(ServiceName.Ini, ProjectName.Cean, identifier, false, file);
		
		return data;
	}
	
	public Resource downloadFile(String identifier, Boolean isProcessed, String filename) {
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		Resource resource = proxy.getFile(ServiceName.Ini, ProjectName.Cean, identifier, isProcessed, filename);
		
		return resource;
	}
	
	public void correctCean(String identifier, String filename) {

		Resource originalFileResource = proxy.getFile(ServiceName.Ini, ProjectName.Cean, identifier, false, filename);
		
		FileVO fileVO = new FileVO();
		
		try {
			Scanner scanner = new Scanner(originalFileResource.getInputStream());
			
			while(scanner.hasNextLine()) {
				String originalLine = scanner.nextLine();
				String[] splitLine = originalLine.split("=");
				String writeLine = "";
				
				if (splitLine[0].equalsIgnoreCase("cEAN") || splitLine[0].equalsIgnoreCase("cEANTrib")) {
					writeLine = splitLine[0] + "=SEM GTIN";
				} else {
					writeLine = originalLine;
				}
				
				fileVO.addLineVO(writeLine);
			}
			
			scanner.close();
			
			proxy.uploadFile(ServiceName.Ini, ProjectName.Cean, identifier, true, filename, fileVO);
		} catch (Exception e) {
			new FileStorageException("It was not possible to correct the file!", e);
		} 
	}
	
}
