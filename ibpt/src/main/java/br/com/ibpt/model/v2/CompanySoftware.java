package br.com.ibpt.model.v2;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "COMPANY_SOFTWARE")
public class CompanySoftware implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FK_COMPANY")
	private Integer fkCompany;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOFTWARE")
	private Software software;
	
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
	
	public CompanySoftware() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Software getSoftware() {
		return software;
	}

	public void setSoftware(Software software) {
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
		return Objects.hash(connection, fkCompanySoftwareSameDb, haveAuthorization, id, isActive, observation, software,
				type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanySoftware other = (CompanySoftware) obj;
		return Objects.equals(connection, other.connection)
				&& Objects.equals(fkCompanySoftwareSameDb, other.fkCompanySoftwareSameDb)
				&& Objects.equals(haveAuthorization, other.haveAuthorization) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(observation, other.observation)
				&& Objects.equals(software, other.software) && Objects.equals(type, other.type);
	}

}
