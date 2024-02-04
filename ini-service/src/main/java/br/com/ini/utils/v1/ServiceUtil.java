package br.com.ini.utils.v1;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

import br.com.ini.exceptions.v1.FileStorageException;

@Service
public class ServiceUtil {
	
	public static File createProcessedFile(String baseFolder, String filename) throws Exception {
		String processedFolder = baseFolder + "\\Processed\\";

		ServiceUtil.createDirectories(Path.of(processedFolder));
		
		File file = new File(processedFolder + filename);
		
		if (file.exists()) file.delete();
		file.createNewFile();
		
		return file;
	}
	
	public static void createDirectories(Path path) {
		try {
			Files.createDirectories(path);
		} catch (Exception e) {
			new FileStorageException("It was not possible to create folder (" + path.toString() + ")!", e);
		}
	}
	
	public static Boolean checkIfFilenameIsInvalid(String filename) {
		return filename.contains("..")
				|| ! (filename.startsWith("NE") || filename.startsWith("NC"))
				|| ! (filename.endsWith("INI"));
	}
	
	public static void writeToFile(File file, String text) {
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(text);
			fw.write("\n");
			fw.close();
		} catch (Exception e) {
			new FileStorageException("It was not possible to write (" + text + ") into file (" + file.getName() + ")!", e);
		}
	}
	
}
