package br.com.conhecimento.integrationtests.vo.wrappers.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KnowledgeWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("_embedded")
	private KnowledgeEmbeddedVO embedded;

	public KnowledgeWrapperVO() {}

	public KnowledgeEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(KnowledgeEmbeddedVO embedded) {
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
		KnowledgeWrapperVO other = (KnowledgeWrapperVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
	
}
