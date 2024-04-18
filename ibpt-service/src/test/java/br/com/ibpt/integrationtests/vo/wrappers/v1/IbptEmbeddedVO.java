package br.com.ibpt.integrationtests.vo.wrappers.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ibpt.integrationtests.vo.v1.IbptVO;

public class IbptEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("ibptVOList")
	private List<IbptVO> ibpts;

	public IbptEmbeddedVO() {}

	public List<IbptVO> getIbpts() {
		return ibpts;
	}

	public void setIbpts(List<IbptVO> ibpts) {
		this.ibpts = ibpts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ibpts);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IbptEmbeddedVO other = (IbptEmbeddedVO) obj;
		return Objects.equals(ibpts, other.ibpts);
	}
	
}
