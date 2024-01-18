package br.com.ibpt.model.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "IBPT", name = "TB_VERSION")
public class Version implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "NAME", unique = true, nullable = false)
	private String name;
	
	@Column(name = "EFFECTIVE_PERIOD_FROM", nullable = false)
	private Date effectivePeriodFrom;
	
	@Column(name = "EFFECTIVE_PERIOD_UNTIL", nullable = false)
	private Date effectivePeriodUntil;
	
	public Version() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
		return Objects.hash(effectivePeriodFrom, effectivePeriodUntil, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Version other = (Version) obj;
		return Objects.equals(effectivePeriodFrom, other.effectivePeriodFrom)
				&& Objects.equals(effectivePeriodUntil, other.effectivePeriodUntil) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}

}
