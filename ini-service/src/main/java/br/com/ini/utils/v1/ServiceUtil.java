package br.com.ini.utils.v1;

import org.springframework.stereotype.Service;

@Service
public class ServiceUtil {
	
	public static Boolean checkIfFilenameIsInvalid(String filename) {
		return filename.contains("..")
				|| ! (filename.startsWith("NE") || filename.startsWith("NC"))
				|| ! (filename.endsWith("INI"));
	}
	
}
