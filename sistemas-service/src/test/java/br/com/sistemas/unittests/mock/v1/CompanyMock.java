package br.com.sistemas.unittests.mock.v1;

import br.com.sistemas.data.vo.v1.CompanyVO;
import br.com.sistemas.model.v1.Company;

public class CompanyMock {

	public Company entity() {
		return entity(0);
	}

	public CompanyVO vo() {
		return vo(0);
	}
	
	public Company entity(Integer number) {
		Company entity = new Company();
		entity.setId(number);
		entity.setCnpj("" + number + number + number + number + number + number + number + number + number + number + number + number + number + number);
		entity.setTradeName("Trade Name" + number);
		entity.setBusinessName("Business Name" + number);
		entity.setObservation("Observation" + number);
		entity.setIsActive(
				number % 2 == 0
				? true
				: false
		);
		entity.setSoftwares(CompanySoftwareMock.entity(number));
		
		return entity;
	}
	
	public CompanyVO vo(Integer number) {
		CompanyVO vo = new CompanyVO();
		vo.setKey(number);
		vo.setCnpj("" + number + number + number + number + number + number + number + number + number + number + number + number + number + number);
		vo.setTradeName("Trade Name" + number);
		vo.setBusinessName("Business Name" + number);
		vo.setObservation("Observation" + number);
		vo.setIsActive(
				number % 2 == 0
				? true
				: false
		);
		vo.setSoftwares(CompanySoftwareMock.entity(number));
		
		return vo;
	}
}
