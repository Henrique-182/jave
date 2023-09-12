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
@Table(name = "COMPANY")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;
	
	@Column(name = "CNPJ", nullable = false, unique = true)
	private String cnpj;
	
	@Column(name = "TRADE_NAME", nullable = false, unique = true)
	private String tradeName;
	
	@Column(name = "BUSINESS_NAME", nullable = false, unique = true)
	private String businessName;
	
	@Column(name = "SOFTWARE", nullable = false)
	private String software;
	
	@Column(name = "HAVE_AUTHORIZATION", nullable = false)
	private Boolean haveAuthorization;
	
	@Column(name = "CONNECTION", nullable = false)
	private String connection;
	
	@Column(name = "OBSERVATION", nullable = true)
	private String observation;
	
	@Column(name = "IS_ACTIVE")
	private Boolean isActive;
	
	@Column(name = "FK_COMPANY_SAME_DB")
	private Integer fkCompanySameDb;

	public Company() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
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

	public Integer getFkCompanySameDb() {
		return fkCompanySameDb;
	}

	public void setFkCompanySameDb(Integer fkCompanySameDb) {
		this.fkCompanySameDb = fkCompanySameDb;
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessName, cnpj, connection, fkCompanySameDb, haveAuthorization, id, isActive,
				observation, software, tradeName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(businessName, other.businessName) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(connection, other.connection)
				&& Objects.equals(fkCompanySameDb, other.fkCompanySameDb)
				&& Objects.equals(haveAuthorization, other.haveAuthorization) && Objects.equals(id, other.id)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(observation, other.observation)
				&& Objects.equals(software, other.software) && Objects.equals(tradeName, other.tradeName);
	}

}
