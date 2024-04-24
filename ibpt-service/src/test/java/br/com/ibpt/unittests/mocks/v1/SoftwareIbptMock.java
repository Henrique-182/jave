package br.com.ibpt.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.model.v1.SoftwareIbpt;

public class SoftwareIbptMock {

	public static SoftwareIbpt entity() {
		return entity(0);
	}
	
	public static List<SoftwareIbpt> list() {
		List<SoftwareIbpt> list = new ArrayList<>();
		for (int i = 0; i < 14; i++) list.add(entity(i));
		
		return list;
	}
	
	public static SoftwareIbpt entity(Integer number) {
		SoftwareIbpt entity = new SoftwareIbpt();
		entity.setKey(number);
		entity.setName("Name" + number);
		
		return entity;
	}
	
}
