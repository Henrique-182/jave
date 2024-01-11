package br.com.sistemas.model.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "SISTEMAS", name = "TB_COMPANY_SOFTWARE")
public class CompanySoftware implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer key;
	
	@Column(name = "FK_COMPANY")
	private Integer fkCompany;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOFTWARE")
	private SoftwareComp software;
	
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
	
	public CompanySoftware() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public SoftwareComp getSoftware() {
		return software;
	}

	public void setSoftware(SoftwareComp software) {
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
		return Objects.hash(connection, fkCompanySoftwareSameDb, haveAuthorization, isActive, key, observation,
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
		CompanySoftware other = (CompanySoftware) obj;
		return Objects.equals(connection, other.connection)
				&& Objects.equals(fkCompanySoftwareSameDb, other.fkCompanySoftwareSameDb)
				&& Objects.equals(haveAuthorization, other.haveAuthorization)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(key, other.key)
				&& Objects.equals(observation, other.observation) && Objects.equals(software, other.software)
				&& Objects.equals(type, other.type);
	}
	
}
