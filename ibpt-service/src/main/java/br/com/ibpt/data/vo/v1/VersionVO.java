package br.com.ibpt.data.vo.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class VersionVO extends RepresentationModel<VersionVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer key;
	private String name;
	private Date effectivePeriodFrom;
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
	
	public Date getEffectivePeriodFrom() {
		return effectivePeriodFrom;
	}

	public void setEffectivePeriodFrom(Date effectivePeriodFrom) {
		this.effectivePeriodFrom = effectivePeriodFrom;
	}

	public Date getEffectivePeriodUntil() {
		return effectivePeriodUntil;
	}

	public void setEffectivePeriodUntil(Date effectivePeriodUntil) {
		this.effectivePeriodUntil = effectivePeriodUntil;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(effectivePeriodFrom, effectivePeriodUntil, key, name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		VersionVO other = (VersionVO) obj;
		return Objects.equals(effectivePeriodFrom, other.effectivePeriodFrom)
				&& Objects.equals(effectivePeriodUntil, other.effectivePeriodUntil) && Objects.equals(key, other.key)
				&& Objects.equals(name, other.name);
	}

}
