package br.com.ini.services.v1;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.exceptions.v1.FileStorageException;
import br.com.ini.utils.v1.ServiceUtil;

@Service
public class CeanInvalidoService {
	
	@Autowired
	private FileStorageService service;

	public String storeFile(String folder, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		return service.storeFile(folder, file);
	}
	
	public Resource downloadFile(String folder, String filename) {
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		return service.loadFileAsResource(folder, filename);
	}
	
	public void correctCean(String folder, String filename) {

		Resource originalFileResource = service.loadFileAsResource(folder, filename);
		
		try {
			String processedFolder = originalFileResource.getFile().getParent() + "\\Processed\\";

			ServiceUtil.createDirectories(Path.of(processedFolder));
			File newFile = new File(processedFolder + filename);
			
			if (newFile.exists()) newFile.delete();
			newFile.createNewFile();
			
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
				
				ServiceUtil.writeToFile(newFile, writeLine);
			}
			
			scanner.close();
		} catch (Exception e) {
			new FileStorageException("It was not possible to correct the file!", e);
		} 
	}
	
}
