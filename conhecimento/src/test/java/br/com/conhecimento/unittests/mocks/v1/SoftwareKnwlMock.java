package br.com.conhecimento.unittests.mocks.v1;

import br.com.conhecimento.model.v1.SoftwareKnwl;

class SoftwareKnwlMock {

	static SoftwareKnwl entity(Integer number) {
		SoftwareKnwl entity = new SoftwareKnwl();
		entity.setId(number);
		entity.setName(number % 2 == 0 ? "Esti" : "Stac");
		
		return entity;
	}
	
}
