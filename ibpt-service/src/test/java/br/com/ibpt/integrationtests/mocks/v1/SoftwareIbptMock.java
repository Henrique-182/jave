package br.com.ibpt.integrationtests.mocks.v1;

import br.com.ibpt.integrationtests.vo.v1.SoftwareVO;
import br.com.ibpt.model.v1.SoftwareIbpt;

public class SoftwareIbptMock {
	
	public SoftwareIbpt mockEntity(Integer number) {
		SoftwareIbpt entity = new SoftwareIbpt();
		entity.setKey(
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
		vo.setKey(
			number % 2 == 0
			? 2
			: 1
		);
		vo.setName("Name" + number);
		
		return vo;
	}
}
