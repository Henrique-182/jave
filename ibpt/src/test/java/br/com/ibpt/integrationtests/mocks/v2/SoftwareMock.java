package br.com.ibpt.integrationtests.mocks.v2;

import br.com.ibpt.integrationtests.vo.v2.SoftwareVO;
import br.com.ibpt.model.v2.Software;

public class SoftwareMock {
	
	public Software mockEntity(Integer number) {
		Software entity = new Software();
		entity.setId(
			number % 2 == 0
			? 2
			: 1
		);
		entity.setName(
			number % 2 == 0
			? "Stac"
			: "Esti"
		);
		
		return entity;
	}
	
	public SoftwareVO mockVO(Integer number) {
		SoftwareVO vo = new SoftwareVO();
		vo.setName("Name" + number);
		
		return vo;
	}
}
