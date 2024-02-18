package br.com.jave.utils.v1;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import br.com.jave.exceptions.v1.FileStorageException;

@Service
public class ServiceUtil {
	
	public static void createDirectories(Path path) {
		try {
			Files.createDirectories(path);
		} catch (Exception e) {
			new FileStorageException("It was not possible to create folder (" + path.toString() + ")!", e);
		}
	}
	
	public static Boolean checkIfFilenameIsInvalid(String filename) {
		return filename.contains("..");
	}
	
}
