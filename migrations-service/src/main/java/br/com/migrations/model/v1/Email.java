package br.com.migrations.model.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Email implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String emailFrom;
	private String emailTo;
	private String subject;
	private String content;
	private ServiceName serviceName;
	private EmailStatus status;
	private Date creationDatetime;
	private Date lastUpdateDatetime;

	public Email() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

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

	public EmailStatus getStatus() {
		return status;
	}

	public void setStatus(EmailStatus status) {
		this.status = status;
	}

	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, creationDatetime, emailFrom, emailTo, id, lastUpdateDatetime, serviceName, status,
				subject);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Email other = (Email) obj;
		return Objects.equals(content, other.content) && Objects.equals(creationDatetime, other.creationDatetime)
				&& Objects.equals(emailFrom, other.emailFrom) && Objects.equals(emailTo, other.emailTo)
				&& Objects.equals(id, other.id) && Objects.equals(lastUpdateDatetime, other.lastUpdateDatetime)
				&& serviceName == other.serviceName && status == other.status && Objects.equals(subject, other.subject);
	}
	
}
