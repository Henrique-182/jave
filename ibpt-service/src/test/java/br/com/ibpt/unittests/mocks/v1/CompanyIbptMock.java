package br.com.ibpt.unittests.mocks.v1;

import br.com.ibpt.model.v1.CompanyIbpt;

public class CompanyIbptMock {

	public static CompanyIbpt entity() {
		return entity(0);
	}

	public static CompanyIbpt entity(Integer number) {
		CompanyIbpt entity = new CompanyIbpt();
		entity.setId(number);
		entity.setCnpj("" + number + number + number + number + number + number + number + number + number + number + number + number + number + number);
		entity.setTradeName("Trade Name" + number);
		entity.setBusinessName("Business Name" + number);
		
		return entity;
	}
	
}
