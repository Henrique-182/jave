package br.com.conhecimento.unittests.mocks.v2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.conhecimento.data.vo.v2.SoftwareVO;
import br.com.conhecimento.model.v2.Software;

public class SoftwareMock {
	
	public Software entity() {
		return entity(0);
	}
	
	public SoftwareVO vo() {
		return vo(0);
	}
	
	public List<Software> entityList() {
		List<Software> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public List<SoftwareVO> voList() {
		List<SoftwareVO> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}

	public Software entity(Integer number) {
		Software entity = new Software();
		entity.setId(number);
		entity.setName("Name" + number);
		entity.setUserLastUpdate(UserAuditMock.entity());
		entity.setLastUpdateDatetime(new Date());
		entity.setUserCreation(UserAuditMock.entity());
		entity.setCreationDatetime(new Date());
		
		return entity;
	}
	
	public SoftwareVO vo(Integer number) {
		SoftwareVO vo = new SoftwareVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		vo.setUserLastUpdate(UserAuditMock.entity());
		vo.setLastUpdateDatetime(new Date());
		vo.setUserCreation(UserAuditMock.entity());
		vo.setCreationDatetime(new Date());
		
		return vo;
	}
	
}
