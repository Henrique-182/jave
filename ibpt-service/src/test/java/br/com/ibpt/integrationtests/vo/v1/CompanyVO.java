package br.com.ibpt.integrationtests.vo.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.ibpt.model.v1.CompanySoftwareIbpt;

public class CompanyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer key;
	private String cnpj;
	private String tradeName;
	private String businessName;
	private String observation;
	private Boolean isActive;
	private List<CompanySoftwareIbpt> softwares;
	
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

	public List<CompanySoftwareIbpt> getSoftwares() {
		return softwares;
	}

	public void setSoftwares(List<CompanySoftwareIbpt> softwares) {
		this.softwares = softwares;
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessName, cnpj, isActive, key, observation, softwares, tradeName);
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
				&& Objects.equals(isActive, other.isActive) && Objects.equals(key, other.key)
				&& Objects.equals(observation, other.observation) && Objects.equals(softwares, other.softwares)
				&& Objects.equals(tradeName, other.tradeName);
	}

}
