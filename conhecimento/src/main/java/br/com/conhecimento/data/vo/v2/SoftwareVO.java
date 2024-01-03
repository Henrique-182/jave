package br.com.conhecimento.data.vo.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.conhecimento.model.v2.UserAudit;

public class SoftwareVO extends RepresentationModel<SoftwareVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private String name;
	private UserAudit userLastUpdate;
	private Date lastUpdateDatetime;
	private UserAudit userCreation;
	private Date creationDatetime;
	
	public SoftwareVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(creationDatetime, key, lastUpdateDatetime, name, userCreation, userLastUpdate);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoftwareVO other = (SoftwareVO) obj;
		return Objects.equals(creationDatetime, other.creationDatetime) && Objects.equals(key, other.key)
				&& Objects.equals(lastUpdateDatetime, other.lastUpdateDatetime) && Objects.equals(name, other.name)
				&& Objects.equals(userCreation, other.userCreation)
				&& Objects.equals(userLastUpdate, other.userLastUpdate);
	}

}
