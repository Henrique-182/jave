package br.com.conhecimento.integrationtests.vo.wrappers.v2;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private UserEmbeddedVO embedded;

	public UserWrapperVO() {}

	public UserEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(UserEmbeddedVO embedded) {
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
		UserWrapperVO other = (UserWrapperVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
	
}
