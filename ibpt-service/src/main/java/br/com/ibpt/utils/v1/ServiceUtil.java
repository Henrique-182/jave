package br.com.ibpt.utils.v1;

import org.springframework.stereotype.Service;

@Service
public class ServiceUtil {
	
	public static Boolean checkIfFilenameIsInvalid(String filename) {
		return filename.contains("..")
				|| ! (filename.startsWith("Ibpt"))
				|| ! (filename.endsWith("zip") || filename.endsWith("rar") || filename.endsWith("7z"));
	}
	
}
