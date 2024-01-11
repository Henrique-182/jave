package br.com.sistemas.unittests.mock.v1;

import br.com.sistemas.model.v1.SoftwareComp;

public class SoftwareCompMock {

	public static SoftwareComp entity() {
		return entity(0);
	}
	
	public static SoftwareComp entity(Integer number) {
		SoftwareComp entity = new SoftwareComp();
		entity.setKey(number);
		entity.setName("Name" + number);
		
		return entity;
	}
	
}
