package br.com.ibpt.data.vo.v2;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.ibpt.model.v2.CompanySoftware;

public class CompanyVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String cnpj;
	private String tradeName;
	private String businessName;
	private String observation;
	private Boolean isActive;
	private List<CompanySoftware> softwares;

	public CompanyVO() {}

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
	
	public List<CompanySoftware> getSoftwares() {
		return softwares;
	}

	public void setSoftwares(List<CompanySoftware> softwares) {
		this.softwares = softwares;
	}

	@Override
	public int hashCode() {
		return Objects.hash(businessName, cnpj, id, isActive, observation, softwares, tradeName);
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
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(observation, other.observation) && Objects.equals(softwares, other.softwares)
				&& Objects.equals(tradeName, other.tradeName);
	}

}
