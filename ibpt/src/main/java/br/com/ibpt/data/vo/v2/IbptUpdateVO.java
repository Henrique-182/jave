package br.com.ibpt.data.vo.v2;

import java.io.Serializable;
import java.util.Objects;

public class IbptUpdateVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Boolean value;
	
	public IbptUpdateVO() {}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Boolean getValue() {
		return value;
	}
	
	public void setValue(Boolean value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, value);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IbptUpdateVO other = (IbptUpdateVO) obj;
		return Objects.equals(id, other.id) && Objects.equals(value, other.value);
	}

}
