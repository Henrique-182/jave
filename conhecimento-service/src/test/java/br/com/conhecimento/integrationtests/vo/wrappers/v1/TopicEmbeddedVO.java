package br.com.conhecimento.integrationtests.vo.wrappers.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.conhecimento.integrationtests.vo.v1.TopicVO;

public class TopicEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("topicVOList")
	private List<TopicVO> topics;

	public TopicEmbeddedVO() {}

	public List<TopicVO> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicVO> topics) {
		this.topics = topics;
	}

	@Override
	public int hashCode() {
		return Objects.hash(topics);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopicEmbeddedVO other = (TopicEmbeddedVO) obj;
		return Objects.equals(topics, other.topics);
	}
	
}
