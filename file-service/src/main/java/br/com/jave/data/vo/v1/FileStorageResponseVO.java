package br.com.jave.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class FileStorageResponseVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filename;
	private String fileDownloadUri;
	private String fileType;
	private long size;
	
	public FileStorageResponseVO() {}
	
	public FileStorageResponseVO(String filename, String fileDownloadUri, String fileType, long size) {
		this.filename = filename;
		this.fileDownloadUri = fileDownloadUri;
		this.fileType = fileType;
		this.size = size;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFileDownloadUri() {
		return fileDownloadUri;
	}

	public void setFileDownloadUri(String fileDownloadUri) {
		this.fileDownloadUri = fileDownloadUri;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileDownloadUri, fileType, filename, size);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileStorageResponseVO other = (FileStorageResponseVO) obj;
		return Objects.equals(fileDownloadUri, other.fileDownloadUri) && Objects.equals(fileType, other.fileType)
				&& Objects.equals(filename, other.filename) && size == other.size;
	}

}
