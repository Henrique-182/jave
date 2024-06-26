package br.com.ibpt.integrationtests.vo.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.ibpt.model.v1.CompanySoftwareIbpt;
import br.com.ibpt.model.v1.VersionIbpt;

public class IbptVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private VersionIbpt version;
	private CompanySoftwareIbpt companySoftware;
	private Boolean isUpdated;
	
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

	@Override
	public int hashCode() {
		return Objects.hash(companySoftware, isUpdated, key, version);
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
		return Objects.equals(companySoftware, other.companySoftware) && Objects.equals(isUpdated, other.isUpdated)
				&& Objects.equals(key, other.key) && Objects.equals(version, other.version);
	}
	
}
