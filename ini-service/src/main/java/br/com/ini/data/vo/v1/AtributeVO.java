package br.com.ini.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class AtributeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String key;
	private String value;
	
	public AtributeVO() {}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
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
		AtributeVO other = (AtributeVO) obj;
		return Objects.equals(key, other.key) && Objects.equals(value, other.value);
	}
	
}
