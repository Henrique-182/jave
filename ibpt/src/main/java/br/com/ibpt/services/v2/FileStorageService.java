package br.com.ibpt.services.v2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ibpt.config.v2.FileStorageConfig;
import br.com.ibpt.exceptions.v2.FileStorageException;
import br.com.ibpt.exceptions.v2.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths
				.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath()
				.normalize();
		
		this.fileStorageLocation = path;
		
		createDirectories(this.fileStorageLocation);
	}
	
	public String storeFile(String versionName, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if (checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
			
			createDirectories(fileStorageLocation.resolve(versionName));
			
			Path targetLocation = this.fileStorageLocation.resolve(versionName).resolve(filename);
			
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return filename;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file (" + filename + "). Please try again", e);
		}
	}
	
	public Resource loadFileAsResource(String versionName, String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(versionName).resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if (resource.exists()) return resource;
			else throw new MyFileNotFoundException("File not found (" + filename + ")");
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found" + filename, e);
		}
	}
	
	private void createDirectories(Path path) {
		try {
			Files.createDirectories(path);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the directory where the uploaded files will be stored!", e);
		}
	}
	
	private Boolean checkIfFilenameIsInvalid(String filename) {
		return filename.contains("..")
			|| ! filename.subSequence(0, 4).equals("Ibpt")
			|| ! (filename.endsWith("zip") || filename.endsWith("rar") || filename.endsWith("7z"));
	}
	
}
