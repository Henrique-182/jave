package br.com.conhecimento.data.vo.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.conhecimento.model.v1.SoftwareKnowledge;
import br.com.conhecimento.model.v1.TopicKnowledge;

public class KnowledgeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private String title;
	private String description;
	private SoftwareKnowledge software;
	private String content;
	private List<TopicKnowledge> topics;
	
	public KnowledgeVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SoftwareKnowledge getSoftware() {
		return software;
	}

	public void setSoftware(SoftwareKnowledge software) {
		this.software = software;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<TopicKnowledge> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicKnowledge> topics) {
		this.topics = topics;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, description, key, software, title, topics);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgeVO other = (KnowledgeVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(description, other.description)
				&& Objects.equals(key, other.key) && Objects.equals(software, other.software)
				&& Objects.equals(title, other.title) && Objects.equals(topics, other.topics);
	}

}
