package br.com.ibpt.integrationtests.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class VersionVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer key;
	private String name;
	private Date effectivePeriodUntil;
	
	public VersionVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEffectivePeriodUntil() {
		return effectivePeriodUntil;
	}

	public void setEffectivePeriodUntil(Date effectivePeriodUntil) {
		this.effectivePeriodUntil = effectivePeriodUntil;
	}

	@Override
	public int hashCode() {
		return Objects.hash(effectivePeriodUntil, key, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionVO other = (VersionVO) obj;
		return Objects.equals(effectivePeriodUntil, other.effectivePeriodUntil) && Objects.equals(key, other.key)
				&& Objects.equals(name, other.name);
	}


}
