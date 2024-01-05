package br.com.ibpt.unittests.mocks.v3;

import br.com.ibpt.model.v3.VersionIbpt;

public class VersionIbptMock {

	public static VersionIbpt entity() {
		return entity();
	}
	
	public static VersionIbpt entity(Integer number) {
		VersionIbpt entity = new VersionIbpt();
		entity.setId(number);
		entity.setName("Name" + number);
		
		return entity;
	}
	
}
