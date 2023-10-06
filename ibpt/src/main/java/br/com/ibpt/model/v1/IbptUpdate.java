package br.com.ibpt.model.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IBPT_UPDATE")
public class IbptUpdate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
    
	@Column(name = "FK_COMPANY", nullable = false)
	private Integer fkCompany;

	@Column(name = "FK_VERSION", nullable = false)
	private Integer fkVersion;
	
	@Column(name = "IS_UPDATED")
	private Boolean isUpdated;

	public IbptUpdate() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFkCompany() {
		return fkCompany;
	}

	public void setFkCompany(Integer fkCompany) {
		this.fkCompany = fkCompany;
	}

	public Integer getFkVersion() {
		return fkVersion;
	}

	public void setFkVersion(Integer fkVersion) {
		this.fkVersion = fkVersion;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fkCompany, fkVersion, id, isUpdated);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IbptUpdate other = (IbptUpdate) obj;
		return Objects.equals(fkCompany, other.fkCompany) && Objects.equals(fkVersion, other.fkVersion)
				&& Objects.equals(id, other.id) && Objects.equals(isUpdated, other.isUpdated);
	}
	
}
