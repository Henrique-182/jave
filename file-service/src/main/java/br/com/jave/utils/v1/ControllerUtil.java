package br.com.jave.utils.v1;

import br.com.jave.models.v1.ProjectName;
import br.com.jave.models.v1.ServiceName;

public class ControllerUtil {

	public static String concatFolder(ServiceName serviceName, ProjectName projectName, String identifier, Boolean isProcessed) {
		
		String path = serviceName.toString() + "/"
					+ projectName.toString() + "/"
					+ identifier + "/"
					+ (isProcessed ? "Processed/" : "/");
			
		return path;
	}
	
}
