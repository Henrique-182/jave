package br.com.ibpt.integrationtests.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class CompanyVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer key;
	private String cnpj;
	private String tradeName;
	private String businessName;
	private String software;
	private Boolean haveAuthorization;
	private String connection;
	private String observation;
	private Boolean isActive;
	private Integer fkCompanySameDb;
	
	public CompanyVO() {}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
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
		return Objects.hash(businessName, cnpj, connection, fkCompanySameDb, haveAuthorization, isActive, key,
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
		CompanyVO other = (CompanyVO) obj;
		return Objects.equals(businessName, other.businessName) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(connection, other.connection)
				&& Objects.equals(fkCompanySameDb, other.fkCompanySameDb)
				&& Objects.equals(haveAuthorization, other.haveAuthorization)
				&& Objects.equals(isActive, other.isActive) && Objects.equals(key, other.key)
				&& Objects.equals(observation, other.observation) && Objects.equals(software, other.software)
				&& Objects.equals(tradeName, other.tradeName);
	}
}
