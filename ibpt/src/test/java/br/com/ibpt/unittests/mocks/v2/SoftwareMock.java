package br.com.ibpt.unittests.mocks.v2;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.data.vo.v3.SoftwareVO;
import br.com.ibpt.model.v2.Software;

public class SoftwareMock {

	public Software mockEntity() {
		return mockEntity(0);
	}
	
	public SoftwareVO mockVO() {
		return mockVO(0);
	}
	
	public List<Software> entityList() {
		List<Software> list = new ArrayList<>();
		for (int i = 0; i < 14; i++) list.add(mockEntity(i));
		
		return list;
	}
	
	public List<SoftwareVO> voList() {
		List<SoftwareVO> list = new ArrayList<>();
		for (int i = 0; i < 14; i++) list.add(mockVO(i));
		
		return list;
	}
	
	public Software mockEntity(Integer number) {
		Software entity = new Software();
		entity.setId(number);
		entity.setName("Name" + number);
		
		return entity;
	}
	
	public SoftwareVO mockVO(Integer number) {
		SoftwareVO vo = new SoftwareVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		
		return vo;
	}
}
