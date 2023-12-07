package br.com.ibpt.unittests.mocks.v3;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.data.vo.v3.UserVO;
import br.com.ibpt.model.v1.Permission;
import br.com.ibpt.model.v1.User;

public class UserMock {

	public UserVO mockVO() {
		return mockVO(0);
	}
	
	public User mockEntity() {
		return mockEntity(0);
	}
	
	public List<UserVO> mockVOList() {
		List<UserVO> voList = new ArrayList<>();
		
		for (int i = 0; i < 14; i++) voList.add(mockVO(i));
		
		return voList;
	}
	
	public List<User> mockEntityList() {
		List<User> entityList = new ArrayList<>();
		
		for (int i = 0; i < 14; i++) entityList.add(mockEntity(i));
		
		return entityList;
	}
	
	public UserVO mockVO(Integer number) {
		UserVO vo = new UserVO();
		vo.setKey(number);
		vo.setUserName("Username" + number);
		vo.setFullName("Full Name" + number);
		vo.setAccountNonExpired(
			number % 2 == 0
			? true
			: false
		);
		vo.setAccountNonLocked(
			number % 2 == 0
			? true
			: false
		);
		vo.setCredentialsNonExpired(
			number % 2 == 0
			? true
			: false
		);
		vo.setEnabled(
			number % 2 == 0
			? true
			: false
		);
		
		List<Permission> permissions = new ArrayList<>();
		Permission p = new Permission();
		p.setId(number);
		p.setDescription("Description" + number);
		permissions.add(p);
		vo.setPermissions(permissions);
		
		return vo;
	}
	
	public User mockEntity(Integer number) {
		User entity = new User();
		entity.setId(number);
		entity.setUserName("Username" + number);
		entity.setFullName("Full Name" + number);
		entity.setPassword("Password" + number);
		entity.setAccountNonExpired(
			number % 2 == 0
			? true
			: false
		);
		entity.setAccountNonLocked(
			number % 2 == 0
			? true
			: false
		);
		entity.setCredentialsNonExpired(
			number % 2 == 0
			? true
			: false
		);
		entity.setEnabled(
			number % 2 == 0
			? true
			: false
		);
		
		List<Permission> permissions = new ArrayList<>();
		Permission p = new Permission();
		p.setId(number);
		p.setDescription("Description" + number);
		permissions.add(p);
		entity.setPermissions(permissions);
		
		return entity;
	}
}
