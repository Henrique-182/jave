package br.com.ibpt.data.vo.v2;

import java.io.Serializable;
import java.util.Objects;

import br.com.ibpt.model.v1.Version;
import br.com.ibpt.model.v2.CompanySoftwareIbpt;

public class IbptVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Version version;
	private CompanySoftwareIbpt companySoftware;
	private Boolean isUpdated;
	
	public IbptVO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
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
		return Objects.hash(companySoftware, id, isUpdated, version);
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
		return Objects.equals(companySoftware, other.companySoftware) && Objects.equals(id, other.id)
				&& Objects.equals(isUpdated, other.isUpdated) && Objects.equals(version, other.version);
	}
	
}
