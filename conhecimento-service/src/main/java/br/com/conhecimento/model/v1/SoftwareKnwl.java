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
@Table(schema = "SISTEMAS", name = "TB_SOFTWARE")
public class SoftwareKnwl implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;
	
	@Column(name = "NAME", nullable = false)
	private String name;

	public SoftwareKnwl() {}
	
	public SoftwareKnwl(Integer id) {
		this.id = id;
		this.name = id == 1 
				? "Esti" 
				: id == 2
				? "Stac"
				: "";
 	}

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
		SoftwareKnwl other = (SoftwareKnwl) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}

}
