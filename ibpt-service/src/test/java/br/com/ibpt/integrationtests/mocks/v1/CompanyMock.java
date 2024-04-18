package br.com.ibpt.integrationtests.mocks.v1;

import br.com.ibpt.integrationtests.vo.v1.CompanyVO;

public class CompanyMock {
	
	CompanySoftwareIbptMock mock = new CompanySoftwareIbptMock();

	public CompanyVO mockVO() {
		return mockVO(0);
	}
	
	public CompanyVO mockVO(Integer number) {
		CompanyVO vo = new CompanyVO();
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
