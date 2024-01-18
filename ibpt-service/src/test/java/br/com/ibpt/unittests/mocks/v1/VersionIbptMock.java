package br.com.ibpt.unittests.mocks.v1;

import br.com.ibpt.model.v1.VersionIbpt;

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
