package br.com.ibpt.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;

public class IbptUpdateVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String versionName;
	private String companyCnpj;
	private String companyTradeName;
	private String companyBusinessName;
	private Boolean isUpdated;
	
	public IbptUpdateVO() {}
	
	public IbptUpdateVO(Integer id, String versionName, String companyCnpj, String companyTradeName,
			String companyBusinessName, Boolean isUpdated) {
		this.id = id;
		this.versionName = versionName;
		this.companyCnpj = companyCnpj;
		this.companyTradeName = companyTradeName;
		this.companyBusinessName = companyBusinessName;
		this.isUpdated = isUpdated;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getCompanyCnpj() {
		return companyCnpj;
	}

	public void setCompanyCnpj(String companyCnpj) {
		this.companyCnpj = companyCnpj;
	}

	public String getCompanyTradeName() {
		return companyTradeName;
	}

	public void setCompanyTradeName(String companyTradeName) {
		this.companyTradeName = companyTradeName;
	}

	public String getCompanyBusinessName() {
		return companyBusinessName;
	}

	public void setCompanyBusinessName(String companyBusinessName) {
		this.companyBusinessName = companyBusinessName;
	}

	public Boolean getIsUpdated() {
		return isUpdated;
	}

	public void setIsUpdated(Boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyBusinessName, companyCnpj, companyTradeName, id, isUpdated, versionName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IbptUpdateVO other = (IbptUpdateVO) obj;
		return Objects.equals(companyBusinessName, other.companyBusinessName)
				&& Objects.equals(companyCnpj, other.companyCnpj)
				&& Objects.equals(companyTradeName, other.companyTradeName) && Objects.equals(id, other.id)
				&& Objects.equals(isUpdated, other.isUpdated) && Objects.equals(versionName, other.versionName);
	}
	
}
