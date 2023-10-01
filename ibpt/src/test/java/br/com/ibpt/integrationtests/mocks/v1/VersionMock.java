package br.com.ibpt.integrationtests.mocks.v1;

import java.util.Date;

import br.com.ibpt.integrationtests.vo.VersionVO;

public class VersionMock {

	public VersionVO mockVO() {
		return mockVO(0);
	}
	
	public VersionVO mockVO(Integer number) {
		VersionVO vo = new VersionVO();
		vo.setName("Name" + number);
		vo.setEffectivePeriodUntil(new Date(number));
		
		return vo;
	}
}
