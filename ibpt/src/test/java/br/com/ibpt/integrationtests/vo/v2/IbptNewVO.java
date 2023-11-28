package br.com.ibpt.integrationtests.vo.v2;

import java.io.Serializable;
import java.util.Objects;

public class IbptNewVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;

	public IbptNewVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IbptNewVO other = (IbptNewVO) obj;
		return Objects.equals(key, other.key);
	}
	
}
