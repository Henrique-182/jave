package br.com.ibpt.model.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "SISTEMAS", name = "TB_COMPANY")
public class CompanyIbpt implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "CNPJ", nullable = false, unique = true)
	private String cnpj;
	
	@Column(name = "TRADE_NAME", nullable = false)
	private String tradeName;
	
	@Column(name = "BUSINESS_NAME", nullable = false)
	private String businessName;
	
	public CompanyIbpt() {}

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

	@Override
	public int hashCode() {
		return Objects.hash(businessName, cnpj, id, tradeName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyIbpt other = (CompanyIbpt) obj;
		return Objects.equals(businessName, other.businessName) && Objects.equals(cnpj, other.cnpj)
				&& Objects.equals(id, other.id) && Objects.equals(tradeName, other.tradeName);
	}

}
