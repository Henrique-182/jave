package br.com.conhecimento.integrationtests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.conhecimento.integrationtests.vo.v1.TopicVO;


public class TopicMock {

	public TopicVO vo() {
		return vo(0);
	}
	
	public List<TopicVO> voList() {
		List<TopicVO> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public TopicVO vo(Integer number) {
		TopicVO vo = new TopicVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		
		return vo;
	}
	
}
