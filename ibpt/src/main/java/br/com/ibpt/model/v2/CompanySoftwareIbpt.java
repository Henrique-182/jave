package br.com.ibpt.model.v2;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMPANY_SOFTWARE")
public class CompanySoftwareIbpt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "FK_COMPANY")
	private CompanyIbpt company;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOFTWARE")
	private SoftwareIbpt software;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "HAVE_AUTHORIZATION")
	private Boolean haveAuthorization;
	
	@Column(name = "CONNECTION")
	private String connection;
	
	@Column(name = "OBSERVATION")
	private String observation;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@Column(name = "FK_COMPANY_SOFTWARE_SAME_DB")
	private Integer fkCompanySoftwareSameDb;
	
	public CompanySoftwareIbpt() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
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
		return Objects.hash(company, connection, fkCompanySoftwareSameDb, haveAuthorization, id, isActive, observation,
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
				&& Objects.equals(haveAuthorization, other.haveAuthorization) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(observation, other.observation)
				&& Objects.equals(software, other.software) && Objects.equals(type, other.type);
	}

}
