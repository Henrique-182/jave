package br.com.ibpt.data.vo.v2;

import java.io.Serializable;
import java.util.Objects;

public class SoftwareVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	
	public SoftwareVO() {}
	
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
		SoftwareVO other = (SoftwareVO) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name);
	}
	
}
