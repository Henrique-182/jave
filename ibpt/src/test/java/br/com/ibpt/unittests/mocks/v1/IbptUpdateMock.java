package br.com.ibpt.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

public class IbptUpdateMock {
	
	public Object[] mockObject() {
		return mockObject(0);
	}

	public List<Object[]> mockObjectList() {
		List<Object[]> objectList = new ArrayList<>();
		
		for (int i = 0; i < 14; i++) objectList.add(mockObject(i));
		
		return objectList;
	}
	
	public Object[] mockObject(Integer number) {
		Object[] object = new Object[6];
		object[0] = number;
		object[1] = "Version Name" + number;
		object[2] = "" + number + number + number + number + number + number + number + number + number + number + number + number + number + number;
		object[3] = "Company Trade Name" + number;
		object[4] = "Company Business Name" + number;
		object[5] = number % 2 == 0
					? true
					: false;
		
		return object;
	}
}
