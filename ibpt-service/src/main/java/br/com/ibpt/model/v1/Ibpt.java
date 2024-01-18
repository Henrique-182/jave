package br.com.ibpt.model.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "IBPT", name = "TB_IBPT")
public class Ibpt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "FK_VERSION")
	private VersionIbpt version;
	
	@ManyToOne
	@JoinColumn(name = "FK_COMPANY_SOFTWARE")
	private CompanySoftwareIbpt companySoftware;
	
	@Column(name = "IS_UPDATED")
	private Boolean isUpdated;
	
	@Column(name = "LAST_UPDATE_DATETIME")
	private Date lastUpdateDatetime;
	
	@Column(name = "CREATION_DATETIME")
	private Date creationDatetime;
	
	public Ibpt() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companySoftware, creationDatetime, id, isUpdated, lastUpdateDatetime, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ibpt other = (Ibpt) obj;
		return Objects.equals(companySoftware, other.companySoftware)
				&& Objects.equals(creationDatetime, other.creationDatetime) && Objects.equals(id, other.id)
				&& Objects.equals(isUpdated, other.isUpdated)
				&& Objects.equals(lastUpdateDatetime, other.lastUpdateDatetime)
				&& Objects.equals(version, other.version);
	}

}
