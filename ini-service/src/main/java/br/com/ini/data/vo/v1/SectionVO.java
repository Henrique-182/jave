package br.com.ini.data.vo.v1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SectionVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private List<AtributeVO> atributes;
	
	public SectionVO() {
		atributes = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AtributeVO> getAtributes() {
		return atributes;
	}

	public void setAtributes(List<AtributeVO> atributes) {
		this.atributes = atributes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(atributes, name);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectionVO other = (SectionVO) obj;
		return Objects.equals(atributes, other.atributes) && Objects.equals(name, other.name);
	}
	
}
