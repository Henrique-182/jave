package br.com.ibpt.data.vo.v2;

import java.io.Serializable;
import java.util.Objects;

public class IbptUpdateVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private Boolean value;
	
	public IbptUpdateVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
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
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}
	
}
