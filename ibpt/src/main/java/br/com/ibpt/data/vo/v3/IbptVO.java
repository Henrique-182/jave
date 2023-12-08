package br.com.ibpt.data.vo.v3;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.ibpt.model.v1.Version;
import br.com.ibpt.model.v2.CompanySoftwareIbpt;

public class IbptVO extends RepresentationModel<IbptVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private Version version;
	private CompanySoftwareIbpt companySoftware;
	private Boolean isUpdated;
	
	public IbptVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
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
		return Objects.hash(companySoftware, key, isUpdated, version);
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
		return Objects.equals(companySoftware, other.companySoftware) && Objects.equals(key, other.key)
				&& Objects.equals(isUpdated, other.isUpdated) && Objects.equals(version, other.version);
	}
	
}
