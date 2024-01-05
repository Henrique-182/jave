package br.com.ibpt.integrationtests.vo.v3;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import br.com.ibpt.model.v3.CompanySoftwareIbpt;
import br.com.ibpt.model.v3.UserAudit;
import br.com.ibpt.model.v3.VersionIbpt;

public class IbptVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private VersionIbpt version;
	private CompanySoftwareIbpt companySoftware;
	private Boolean isUpdated;
	private UserAudit userLastUpdate;
	private Date lastUpdateDatetime;
	private UserAudit userCreation;
	private Date creationDatetime;
	
	public IbptVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public VersionIbpt getVersion() {
		return version;
	}

	public void setVersion(VersionIbpt version) {
		this.version = version;
	}

	public CompanySoftwareIbpt getCompanySoftware() {
		return companySoftware;
	}

	public void setCompanySoftware(CompanySoftwareIbpt companySoftware) {
		this.companySoftware = companySoftware;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
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
		return Objects.hash(companySoftware, creationDatetime, isUpdated, key, lastUpdateDatetime, userCreation,
				userLastUpdate, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IbptVO other = (IbptVO) obj;
		return Objects.equals(companySoftware, other.companySoftware)
				&& Objects.equals(creationDatetime, other.creationDatetime)
				&& Objects.equals(isUpdated, other.isUpdated) && Objects.equals(key, other.key)
				&& Objects.equals(lastUpdateDatetime, other.lastUpdateDatetime)
				&& Objects.equals(userCreation, other.userCreation)
				&& Objects.equals(userLastUpdate, other.userLastUpdate) && Objects.equals(version, other.version);
	}

}
