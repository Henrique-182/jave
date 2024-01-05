package br.com.ibpt.model.v3;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "VERSION")
public class VersionIbpt implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;

	public VersionIbpt() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionIbpt other = (VersionIbpt) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
}
