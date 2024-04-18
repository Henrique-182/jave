package br.com.ibpt.integrationtests.vo.wrappers.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperIbptVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private IbptEmbeddedVO embedded;

	public WrapperIbptVO() {}

	public IbptEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(IbptEmbeddedVO embedded) {
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
		WrapperIbptVO other = (WrapperIbptVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
	
}
