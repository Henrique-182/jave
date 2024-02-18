package br.com.jave.exceptions.v1;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileStorageException(String ex) {
		super(ex);
	}
	
	public FileStorageException(String ex, Throwable cause) {
		super(ex, cause);
	}

}
