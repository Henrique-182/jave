package br.com.mail.model.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "EMAIL", name = "TB_EMAIL")
public class Email implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "EMAIL_FROM", nullable = false)
	private String emailFrom;
	
	@Column(name = "EMAIL_TO", nullable = false)
	private String emailTo;
	
	@Column(name = "SUBJECT", nullable = false)
	private String subject;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@Column(name = "SERVICE_NAME", nullable = false)
	private ServiceName serviceName;
	
	@Column(name = "STATUS", nullable = false)
	private EmailStatus status;
	
	@Column(name = "CREATION_DATETIME", nullable = false)
	private Date creationDatetime;
	
	@Column(name = "LAST_UPDATE_DATETIME", nullable = false)
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
