package br.com.ibpt.unittests.mocks.v2;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.model.v2.SoftwareIbpt;

public class SoftwareIbptMock {

	public SoftwareIbpt mockEntity() {
		return mockEntity(0);
	}
	
	public List<SoftwareIbpt> entityList() {
		List<SoftwareIbpt> list = new ArrayList<>();
		for (int i = 0; i < 14; i++) list.add(mockEntity(i));
		
		return list;
	}
	
	public SoftwareIbpt mockEntity(Integer number) {
		SoftwareIbpt entity = new SoftwareIbpt();
		entity.setId(number);
		entity.setName("Name" + number);
		
		return entity;
	}
	
}
