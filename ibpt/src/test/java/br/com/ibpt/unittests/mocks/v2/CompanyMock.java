package br.com.ibpt.unittests.mocks.v2;

import br.com.ibpt.data.vo.v2.CompanyVO;
import br.com.ibpt.model.v2.Company;

public class CompanyMock {

	CompanySoftwareMock mock = new CompanySoftwareMock();
	
	public Company mockEntity() {
		return mockEntity(0);
	}

	public CompanyVO mockVO() {
		return mockVO(0);
	}
	
	public Company mockEntity(Integer number) {
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
		entity.setSoftwares(mock.mockEntity(number));
		
		return entity;
	}
	
	public CompanyVO mockVO(Integer number) {
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
		vo.setSoftwares(mock.mockEntity(number));
		
		return vo;
	}
}
