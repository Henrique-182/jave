package br.com.ini.utils.v1;

public class ControllerUtil {

	private static final String CEAN_BASE_FOLDER = "CEAN/";
	
	public static String concatFolder(String project, String cnpj) {
		String folder = "";
		folder += project.equalsIgnoreCase("cean") ? CEAN_BASE_FOLDER : "";
		folder += cnpj + "/";

		return folder;
	}
	
	public static String concatFolder(String project, String cnpj, Boolean processed) {
		String folder = "";
		folder += project.equalsIgnoreCase("cean") ? CEAN_BASE_FOLDER : "";
		folder += cnpj + "/";
		folder += processed ? "processed/" : "";

		return folder;
	}

}
