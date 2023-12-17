package br.com.conhecimento.integrationtests.mocks.v1;

import br.com.conhecimento.model.v1.SoftwareKnwl;

class SoftwareKnwlMock {

	static SoftwareKnwl entity(Integer number) {
		SoftwareKnwl entity = new SoftwareKnwl();
		entity.setId(number % 2 == 0 ? 1 : 2);
		entity.setName(number % 2 == 0 ? "Esti" : "Stac");
		
		return entity;
	}
	
}
