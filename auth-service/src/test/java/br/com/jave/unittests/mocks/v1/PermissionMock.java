package br.com.jave.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.jave.model.v1.Permission;

public class PermissionMock {

	public static Permission entity() {
		return entity(0);
	}
	
	public static List<Permission> entityList() {
		List<Permission> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public static Permission entity(Integer id) {
		Permission entity = new Permission();
		entity.setId(id);
		entity.setDescription("Description" + id);
		
		return entity;
	}
	
}
