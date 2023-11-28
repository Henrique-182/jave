package br.com.ibpt.unittests.mocks.v2;

import br.com.ibpt.model.v2.CompanyIbpt;

public class CompanyIbptMock {

	public CompanyIbpt mockEntity() {
		return mockEntity(0);
	}

	public CompanyIbpt mockEntity(Integer number) {
		CompanyIbpt entity = new CompanyIbpt();
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
		
		return entity;
	}
	
}
