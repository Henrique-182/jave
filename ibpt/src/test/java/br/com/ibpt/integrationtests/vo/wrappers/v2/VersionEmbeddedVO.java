package br.com.ibpt.integrationtests.vo.wrappers.v2;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ibpt.integrationtests.vo.v1.VersionVO;

public class VersionEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("versionVOList")
	private List<VersionVO> versions;

	public VersionEmbeddedVO() {}

	public List<VersionVO> getVersions() {
		return versions;
	}

	public void setVersions(List<VersionVO> versions) {
		this.versions = versions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(versions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionEmbeddedVO other = (VersionEmbeddedVO) obj;
		return Objects.equals(versions, other.versions);
	}
	
}
