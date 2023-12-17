package br.com.conhecimento.integrationtests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.conhecimento.integrationtests.vo.v1.SoftwareVO;

public class SoftwareMock {

	public SoftwareVO vo() {
		return vo(0);
	}
	
	public List<SoftwareVO> voList() {
		List<SoftwareVO> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}

	public SoftwareVO vo(Integer number) {
		SoftwareVO vo = new SoftwareVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		
		return vo;
	}
	
}
