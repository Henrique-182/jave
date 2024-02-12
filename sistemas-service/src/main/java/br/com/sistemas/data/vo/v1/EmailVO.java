package br.com.sistemas.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.sistemas.model.v1.ServiceName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Email
	private String emailTo;
	
	@NotNull
	private String subject;
	
	@NotNull
	private String content;
	
	@NotNull
	private ServiceName serviceName;
	
	public EmailVO() {}

	public String getEmailTo() {
		return emailTo;
	}

	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ServiceName getServiceName() {
		return serviceName;
	}

	public void setServiceName(ServiceName serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, emailTo, serviceName, subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailVO other = (EmailVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(emailTo, other.emailTo)
				&& serviceName == other.serviceName && Objects.equals(subject, other.subject);
	}

}
