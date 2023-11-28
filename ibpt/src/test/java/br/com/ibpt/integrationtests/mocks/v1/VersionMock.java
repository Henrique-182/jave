package br.com.ibpt.integrationtests.mocks.v1;

import java.util.Date;

import br.com.ibpt.integrationtests.vo.v1.VersionVO;
import br.com.ibpt.model.v1.Version;

public class VersionMock {

	public Version mockEntity() {
		return mockEntity(0);
	}
	
	public VersionVO mockVO() {
		return mockVO(0);
	}
	
	public Version mockEntity(Integer number) {
		Version entity = new Version();
		entity.setName("Name" + number);
		entity.setEffectivePeriodUntil(new Date(number));
		
		return entity;
	}
	
	public VersionVO mockVO(Integer number) {
		VersionVO vo = new VersionVO();
		vo.setName("Name" + number);
		vo.setEffectivePeriodUntil(new Date(number));
		
		return vo;
	}
}
