package br.com.ini.utils.v1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.stereotype.Service;

@Service
public class FileServiceUtil {
	
	public static void createDirectories(Path path) {
		try {
			Files.createDirectories(path);
		} catch (Exception e) {
			
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
