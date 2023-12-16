package br.com.conhecimento.model.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "KNOWLEDGE")
public class Knowledge implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOFTWARE")
	private SoftwareKnowledge software;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "KNOWLEDGE_TOPIC",
		joinColumns = @JoinColumn(name = "FK_KNOWLEDGE"),
		inverseJoinColumns = @JoinColumn(name = "FK_TOPIC")
	)
	private List<TopicKnowledge> topics;

	public Knowledge() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return Objects.hash(content, description, id, software, title, topics);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Knowledge other = (Knowledge) obj;
		return Objects.equals(content, other.content) && Objects.equals(description, other.description)
				&& Objects.equals(id, other.id) && Objects.equals(software, other.software)
				&& Objects.equals(title, other.title) && Objects.equals(topics, other.topics);
	}

}
