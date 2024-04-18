package br.com.sistemas.integrationtests.mocks.v1;

import br.com.sistemas.integrationtests.vo.v1.SoftwareVO;
import br.com.sistemas.model.v1.SoftwareComp;

public class SoftwareMock {
	
	public SoftwareComp mockEntity(Integer number) {
		SoftwareComp entity = new SoftwareComp();
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
		vo.setName("Name" + number);
		
		return vo;
	}
}
