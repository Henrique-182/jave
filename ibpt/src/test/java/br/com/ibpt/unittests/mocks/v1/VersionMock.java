package br.com.ibpt.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.model.v1.Version;

public class VersionMock {

	public Version mockEntity() {
		return mockEntity(0);
	}
	
	public VersionVO mockVO() {
		return mockVO(0);
	}
	
	public List<Version> mockEntityList() {
		List<Version> entityList = new ArrayList<>();
		for (int i = 0; i < 14; i++) entityList.add(mockEntity(i));
		return entityList;
	}
	
	public List<VersionVO> mockVOList() {
		List<VersionVO> voList = new ArrayList<>();
		for (int i = 0; i < 14; i++) voList.add(mockVO(i));
		return voList;
	}
	
	public Version mockEntity(Integer number) {
		Version entity = new Version();
		entity.setId(number);
		entity.setName("Name" + number);
		entity.setEffectivePeriodUntil(new Date(number));
		
		return entity;
	}
	
	public VersionVO mockVO(Integer number) {
		VersionVO vo = new VersionVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		vo.setEffectivePeriodUntil(new Date(number));
		
		return vo;
	}
} 
