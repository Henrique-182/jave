package br.com.ini.services.v1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.configs.v1.FileStorageConfig;
import br.com.ini.exceptions.v1.FileStorageException;
import br.com.ini.exceptions.v1.MyFileNotFoundException;
import br.com.ini.utils.v1.FileServiceUtil;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths
				.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath()
				.normalize();
		
		this.fileStorageLocation = path;
		
		FileServiceUtil.createDirectories(this.fileStorageLocation);
	}
	
	public String storeFile(String folder, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			FileServiceUtil.createDirectories(fileStorageLocation.resolve(folder));
			
			Path targetLocation = this.fileStorageLocation.resolve(folder).resolve(filename);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return filename;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file (" + filename + "). Please try again", e);
		}
	}
	
	public Resource loadFileAsResource(String folder, String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(folder).resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) return resource;
			else throw new MyFileNotFoundException("File not found" + filename);
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found" + filename, e);
		}
	}
	
}
