package br.com.conhecimento.integrationtests.vo.wrappers.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.conhecimento.integrationtests.vo.v1.KnowledgeVO;

public class KnowledgeEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("knowledgeVOList")
	private List<KnowledgeVO> knowledges;

	public KnowledgeEmbeddedVO() {}

	public List<KnowledgeVO> getKnowledges() {
		return knowledges;
	}

	public void setKnowledges(List<KnowledgeVO> knowledges) {
		this.knowledges = knowledges;
	}

	@Override
	public int hashCode() {
		return Objects.hash(knowledges);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgeEmbeddedVO other = (KnowledgeEmbeddedVO) obj;
		return Objects.equals(knowledges, other.knowledges);
	}

}
