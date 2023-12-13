package br.com.conhecimento.model.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name = "FK_SOFTWARE", nullable = false)
	private String fkSoftware;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;

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
		return Objects.hash(content, description, fkSoftware, id, title);
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
				&& Objects.equals(fkSoftware, other.fkSoftware) && Objects.equals(id, other.id)
				&& Objects.equals(title, other.title);
	}
	
}
