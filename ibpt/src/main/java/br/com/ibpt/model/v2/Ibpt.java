package br.com.ibpt.model.v2;

import java.io.Serializable;
import java.util.Objects;

import br.com.ibpt.model.v1.Version;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "IBPT")
public class Ibpt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "FK_VERSION")
	private Version version;
	
	@ManyToOne
	@JoinColumn(name = "FK_COMPANY_SOFTWARE")
	private CompanySoftwareIbpt companySoftware;
	
	@Column(name = "IS_UPDATED")
	private Boolean isUpdated;

	public Ibpt() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public CompanySoftwareIbpt getCompanySoftware() {
		return companySoftware;
	}

	public void setCompanySoftware(CompanySoftwareIbpt companySoftware) {
		this.companySoftware = companySoftware;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companySoftware, id, isUpdated, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ibpt other = (Ibpt) obj;
		return Objects.equals(companySoftware, other.companySoftware) && Objects.equals(id, other.id)
				&& Objects.equals(isUpdated, other.isUpdated) && Objects.equals(version, other.version);
	}

}
