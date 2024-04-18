package br.com.ibpt.integrationtests.vo.wrappers.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperVersionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private VersionEmbeddedVO embedded;

	public WrapperVersionVO() {}

	public VersionEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(VersionEmbeddedVO embedded) {
		this.embedded = embedded;
	}

	@Override
	public int hashCode() {
		return Objects.hash(embedded);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WrapperVersionVO other = (WrapperVersionVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
	
}
