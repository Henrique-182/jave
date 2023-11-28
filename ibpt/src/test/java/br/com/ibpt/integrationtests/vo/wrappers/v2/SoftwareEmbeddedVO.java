package br.com.ibpt.integrationtests.vo.wrappers.v2;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ibpt.integrationtests.vo.v2.SoftwareVO;

public class SoftwareEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("softwareVOList")
	private List<SoftwareVO> softwares;

	public SoftwareEmbeddedVO() {}

	public List<SoftwareVO> getSoftwares() {
		return softwares;
	}

	public void setSoftwares(List<SoftwareVO> softwares) {
		this.softwares = softwares;
	}

	@Override
	public int hashCode() {
		return Objects.hash(softwares);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoftwareEmbeddedVO other = (SoftwareEmbeddedVO) obj;
		return Objects.equals(softwares, other.softwares);
	}
	
}
