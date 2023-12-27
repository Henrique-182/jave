package br.com.conhecimento.unittests.mocks.v2;

import br.com.conhecimento.model.v2.User;

public class UserMock {

	public User entity() {
		return entity(0);
	}
	
	public User entity(Integer id) {
		User entity = new User();
		entity.setId(id);
		entity.setUsername("Username" + id);
		entity.setFullname("Fullname" + id);
		entity.setPassword("Password" + id);
		entity.setAccountNonExpired(id % 2 == 0 ? true : false);
		entity.setAccountNonLocked(id % 2 == 0 ? true : false);
		entity.setCredentialsNonExpired(id % 2 == 0 ? true : false);
		entity.setEnabled(id % 2 == 0 ? true : false);
		entity.setPermissions(PermissionMock.entityList());
		
		return entity;
	}
}
