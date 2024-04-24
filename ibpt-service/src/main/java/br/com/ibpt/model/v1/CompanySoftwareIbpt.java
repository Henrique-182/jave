package br.com.ibpt.model.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "SISTEMAS", name = "TB_COMPANY_SOFTWARE")
public class CompanySoftwareIbpt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer key;
	
	@ManyToOne
	@JoinColumn(name = "FK_COMPANY", nullable = false)
	private CompanyIbpt company;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOFTWARE", nullable = false)
	private SoftwareIbpt software;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private SoftwareType type;
	
	@Column(name = "HAVE_AUTHORIZATION", nullable = false)
	private Boolean haveAuthorization;
	
	@Column(name = "CONNECTION_ID", nullable = false)
	private String connection;
	
	@Column(name = "OBSERVATION", nullable = false)
	private String observation;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;
	
	@Column(name = "FK_COMPANY_SOFTWARE_SAME_DB", nullable = true)
	private Integer fkCompanySoftwareSameDb;
	
	public CompanySoftwareIbpt() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public CompanyIbpt getCompany() {
		return company;
	}

	public void setCompany(CompanyIbpt company) {
		this.company = company;
	}

	public SoftwareIbpt getSoftware() {
		return software;
	}

	public void setSoftware(SoftwareIbpt software) {
		this.software = software;
	}

	public SoftwareType getType() {
		return type;
	}

	public void setType(SoftwareType type) {
		this.type = type;
	}

	public Boolean getHaveAuthorization() {
		return haveAuthorization;
	}

	public void setHaveAuthorization(Boolean haveAuthorization) {
		this.haveAuthorization = haveAuthorization;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getFkCompanySoftwareSameDb() {
		return fkCompanySoftwareSameDb;
	}

	public void setFkCompanySoftwareSameDb(Integer fkCompanySoftwareSameDb) {
		this.fkCompanySoftwareSameDb = fkCompanySoftwareSameDb;
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, connection, fkCompanySoftwareSameDb, haveAuthorization, isActive, key, observation,
				software, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanySoftwareIbpt other = (CompanySoftwareIbpt) obj;
		return Objects.equals(company, other.company) && Objects.equals(connection, other.connection)
				&& Objects.equals(fkCompanySoftwareSameDb, other.fkCompanySoftwareSameDb)
				&& Objects.equals(haveAuthorization, other.haveAuthorization)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(key, other.key)
				&& Objects.equals(observation, other.observation) && Objects.equals(software, other.software)
				&& type == other.type;
	}

}
