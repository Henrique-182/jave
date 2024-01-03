package br.com.conhecimento.model.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TOPIC")
public class Topic implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER_LAST_UPDATE")
	private UserAudit userLastUpdate;
	
	@Column(name = "LAST_UPDATE_DATETIME", nullable = false)
	private Date lastUpdateDatetime;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER_CREATION")
	private UserAudit userCreation;
	
	@Column(name = "CREATION_DATETIME", nullable = false)
	private Date creationDatetime;
	
	public Topic() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public UserAudit getUserLastUpdate() {
		return userLastUpdate;
	}

	public void setUserLastUpdate(UserAudit userLastUpdate) {
		this.userLastUpdate = userLastUpdate;
	}

	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public UserAudit getUserCreation() {
		return userCreation;
	}

	public void setUserCreation(UserAudit userCreation) {
		this.userCreation = userCreation;
	}

	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationDatetime, id, lastUpdateDatetime, name, userCreation, userLastUpdate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		return Objects.equals(creationDatetime, other.creationDatetime) && Objects.equals(id, other.id)
				&& Objects.equals(lastUpdateDatetime, other.lastUpdateDatetime) && Objects.equals(name, other.name)
				&& Objects.equals(userCreation, other.userCreation)
				&& Objects.equals(userLastUpdate, other.userLastUpdate);
	}

}
