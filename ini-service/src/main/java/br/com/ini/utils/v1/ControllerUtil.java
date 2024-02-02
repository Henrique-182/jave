package br.com.ini.utils.v1;

public class ControllerUtil {

	private static final String CEAN_BASE_FOLDER = "CEAN/";
	private static final String ICMS_BASE_FOLDER = "ICMS/";
	
	public static String concatFolder(String project, String cnpj) {
		String folder = "";
		folder += projectBaseFolder(project);
		folder += cnpj + "/";

		return folder;
	}
	
	public static String concatFolder(String project, String cnpj, Boolean processed) {
		String folder = "";
		folder += projectBaseFolder(project);
		folder += cnpj + "/";
		folder += processed ? "processed/" : "";

		return folder;
	}
	
	private static String projectBaseFolder(String projectName) {
		return projectName.equalsIgnoreCase("cean") ? CEAN_BASE_FOLDER 
				: projectName.equalsIgnoreCase("icms") ? ICMS_BASE_FOLDER
				: "";
	}

}
