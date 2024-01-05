package br.com.ibpt.unittests.mocks.v3;

import br.com.ibpt.model.v3.UserAudit;

public class UserAuditMock {

	public static UserAudit entity() {
		return entity(0);
	}
	
	public static UserAudit entity(Integer number) {
		UserAudit entity = new UserAudit();
		entity.setId(number);
		entity.setUsername("Username" + number);
		entity.setFullName("Fullname" + number);
		
		return entity;
	}
	
}
