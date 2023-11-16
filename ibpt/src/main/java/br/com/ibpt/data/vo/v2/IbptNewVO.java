package br.com.ibpt.data.vo.v2;

import java.io.Serializable;
import java.util.Objects;

public class IbptNewVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	public IbptNewVO() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}
	
}
