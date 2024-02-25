package br.com.ini.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class LineVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String value;

	public LineVO() {}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineVO other = (LineVO) obj;
		return Objects.equals(value, other.value);
	}
	
}
