package br.com.conhecimento.unittests.mocks.v2;

import br.com.conhecimento.data.vo.v2.UserVO;
import br.com.conhecimento.model.v2.User;

public class UserMock {

	public User entity() {
		return entity(0);
	}
	
	public UserVO vo() {
		return vo(0);
	}
	
	public User entity(Integer number) {
		User entity = new User();
		entity.setId(number);
		entity.setUsername("Username" + number);
		entity.setFullname("Fullname" + number);
		entity.setPassword("Password" + number);
		entity.setAccountNonExpired(number % 2 == 0 ? true : false);
		entity.setAccountNonLocked(number % 2 == 0 ? true : false);
		entity.setCredentialsNonExpired(number % 2 == 0 ? true : false);
		entity.setEnabled(number % 2 == 0 ? true : false);
		entity.setPermissions(PermissionMock.entityList());
		
		return entity;
	}
	
	public UserVO vo(Integer number) {
		UserVO vo = new UserVO();
		vo.setKey(number);
		vo.setUsername("Username" + number);
		vo.setFullname("Fullname" + number);
		vo.setAccountNonExpired(number % 2 == 0 ? true : false);
		vo.setAccountNonLocked(number % 2 == 0 ? true : false);
		vo.setCredentialsNonExpired(number % 2 == 0 ? true : false);
		vo.setEnabled(number % 2 == 0 ? true : false);
		vo.setPermissions(PermissionMock.entityList());
		
		return vo;
	}
	
}
