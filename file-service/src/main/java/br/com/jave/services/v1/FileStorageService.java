package br.com.jave.services.v1;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.jave.configs.v1.FileStorageConfig;
import br.com.jave.data.vo.v1.LineVO;
import br.com.jave.data.vo.v1.FileVO;
import br.com.jave.exceptions.v1.FileStorageException;
import br.com.jave.exceptions.v1.MyFileNotFoundException;
import br.com.jave.utils.v1.ServiceUtil;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths
				.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath()
				.normalize();
		
		this.fileStorageLocation = path;
		
		ServiceUtil.createDirectories(this.fileStorageLocation);
	}
	
	public String storeFile(String folder, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Invalid filename (" + filename + ")!");
		
		try {
			ServiceUtil.createDirectories(fileStorageLocation.resolve(folder));
			
			Path targetLocation = this.fileStorageLocation.resolve(folder).resolve(filename);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return filename;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file (" + filename + "). Please try again", e);
		}
	}
	
	public File storeFile(String folder, String filename, FileVO data) {
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Invalid filename (" + filename + ")!");
		
		try {
			ServiceUtil.createDirectories(fileStorageLocation.resolve(folder));
			
			Path targetLocation = this.fileStorageLocation.resolve(folder).resolve(filename);
			
			File file = new File(targetLocation.toString());
			
			if (file.exists()) file.delete();
			file.createNewFile();
			
			listToFile(file, data);
			
			return file;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file (" + filename + "). Please try again", e);
		}
	}
	
	public Resource loadFileAsResource(String folder, String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(folder).resolve(filename).normalize();
			Resource resource = new UrlResource(filePath.toUri());

			if (resource.exists()) return resource;
			else throw new MyFileNotFoundException("File not found (" + filename + ")!");
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found (" + filename + ")!", e);
		}
	}
	
	public static void listToFile(File file, FileVO data) {
		
		List<LineVO> lines = data.getLines();
		
		for (int i = 0; i < lines.size(); i++) ServiceUtil.writeToFile(file, lines.get(i).getValue());
	}
	
}
