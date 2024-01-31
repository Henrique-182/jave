package br.com.ini.exceptions.v1;

public class MyFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyFileNotFoundException(String ex) {
		super(ex);
	}
	
	public MyFileNotFoundException(String ex, Throwable cause) {
		super(ex, cause);
	}
	
}
