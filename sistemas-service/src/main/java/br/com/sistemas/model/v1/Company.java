package br.com.sistemas.model.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "SISTEMAS", name = "TB_COMPANY")
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "CNPJ", nullable = false, unique = true)
	private String cnpj;
	
	@Column(name = "TRADE_NAME", nullable = false)
	private String tradeName;
	
	@Column(name = "BUSINESS_NAME", nullable = false)
	private String businessName;
	
	@Column(name = "OBSERVATION", nullable = true)
	private String observation;
	
	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean isActive;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "FK_COMPANY")
	private List<CompanySoftware> softwares;
	
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

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	
	public List<CompanySoftware> getSoftwares() {
		return softwares;
	}

	public void setSoftwares(List<CompanySoftware> softwares) {
		this.softwares = softwares;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
		Company other = (Company) obj;
		return Objects.equals(businessName, other.businessName) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(id, other.id) && Objects.equals(isActive, other.isActive)
				&& Objects.equals(observation, other.observation) && Objects.equals(softwares, other.softwares)
				&& Objects.equals(tradeName, other.tradeName);
	}

}
