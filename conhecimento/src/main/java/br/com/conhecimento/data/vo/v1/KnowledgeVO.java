package br.com.conhecimento.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class KnowledgeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private String title;
	private String description;
	private String fkSoftware;
	private String content;
	
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

	public String getFkSoftware() {
		return fkSoftware;
	}

	public void setFkSoftware(String fkSoftware) {
		this.fkSoftware = fkSoftware;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, description, fkSoftware, key, title);
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
				&& Objects.equals(fkSoftware, other.fkSoftware) && Objects.equals(key, other.key)
				&& Objects.equals(title, other.title);
	}

}
