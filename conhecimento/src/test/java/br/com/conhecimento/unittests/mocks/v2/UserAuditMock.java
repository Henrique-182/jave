package br.com.conhecimento.unittests.mocks.v2;

import br.com.conhecimento.model.v2.UserAudit;

public class UserAuditMock {

	public static UserAudit entity() {
		return entity(0);
	}
	
	public static UserAudit entity(Integer number) {
		UserAudit entity = new UserAudit();
		entity.setId(number);
		entity.setUsername("Username" + number);
		entity.setFullname("Fullname" + number);
		
		return entity;
	}
	
}
