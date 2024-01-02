package br.com.conhecimento.data.vo.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.conhecimento.model.v2.SoftwareKnwl;
import br.com.conhecimento.model.v2.TopicKnwl;
import br.com.conhecimento.model.v2.UserAudit;

public class KnowledgeVO extends RepresentationModel<KnowledgeVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer key;
	private String title;
	private String description;
	private SoftwareKnwl software;
	private String content;
	private UserAudit userLastUpdate;
	private Date lastUpdateDatetime;
	private UserAudit userCreation;
	private Date creationDatetime;
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
	
	public UserAudit getUserLastUpdate() {
		return userLastUpdate;
	}

	public void setUserLastUpdate(UserAudit userLastUpdate) {
		this.userLastUpdate = userLastUpdate;
	}

	public Date getLastUpdateDatetime() {
		return lastUpdateDatetime;
	}

	public void setLastUpdateDatetime(Date lastUpdateDatetime) {
		this.lastUpdateDatetime = lastUpdateDatetime;
	}

	public UserAudit getUserCreation() {
		return userCreation;
	}

	public void setUserCreation(UserAudit userCreation) {
		this.userCreation = userCreation;
	}

	public Date getCreationDatetime() {
		return creationDatetime;
	}

	public void setCreationDatetime(Date creationDatetime) {
		this.creationDatetime = creationDatetime;
	}

	public List<TopicKnwl> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicKnwl> topics) {
		this.topics = topics;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(content, creationDatetime, description, key, lastUpdateDatetime,
				software, title, topics, userCreation, userLastUpdate);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgeVO other = (KnowledgeVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(creationDatetime, other.creationDatetime)
				&& Objects.equals(description, other.description) && Objects.equals(key, other.key)
				&& Objects.equals(lastUpdateDatetime, other.lastUpdateDatetime)
				&& Objects.equals(software, other.software) && Objects.equals(title, other.title)
				&& Objects.equals(topics, other.topics) && Objects.equals(userCreation, other.userCreation)
				&& Objects.equals(userLastUpdate, other.userLastUpdate);
	}

}
