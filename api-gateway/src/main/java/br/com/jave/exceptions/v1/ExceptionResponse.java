package br.com.jave.exceptions.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date timestamp;
	private String message;
	private String details;
	
	public ExceptionResponse() {}

	public ExceptionResponse(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDetails() {
		return details;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(details, message, timestamp);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExceptionResponse other = (ExceptionResponse) obj;
		return Objects.equals(details, other.details) && Objects.equals(message, other.message)
				&& Objects.equals(timestamp, other.timestamp);
	}
	
}
