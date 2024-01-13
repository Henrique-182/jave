package br.com.conhecimento.data.vo.v1;

import java.io.Serializable;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import br.com.conhecimento.model.v1.SoftwareKnwl;
import br.com.conhecimento.model.v1.TopicKnwl;

public class KnowledgeVO extends RepresentationModel<KnowledgeVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer key;
	private String title;
	private String description;
	private SoftwareKnwl software;
	private String content;
	private List<TopicKnwl> topics;
	
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

	public SoftwareKnwl getSoftware() {
		return software;
	}

	public void setSoftware(SoftwareKnwl software) {
		this.software = software;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

	public List<TopicKnwl> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicKnwl> topics) {
		this.topics = topics;
	}

	

}
