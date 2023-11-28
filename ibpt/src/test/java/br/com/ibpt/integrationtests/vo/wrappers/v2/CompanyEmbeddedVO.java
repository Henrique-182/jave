package br.com.ibpt.integrationtests.vo.wrappers.v2;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.ibpt.integrationtests.vo.v2.CompanyVO;

public class CompanyEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("companyVOList")
	private List<CompanyVO> companies;

	public CompanyEmbeddedVO() {}

	public List<CompanyVO> getCompanies() {
		return companies;
	}

	public void setCompanies(List<CompanyVO> companies) {
		this.companies = companies;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companies);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyEmbeddedVO other = (CompanyEmbeddedVO) obj;
		return Objects.equals(companies, other.companies);
	}
	
}
