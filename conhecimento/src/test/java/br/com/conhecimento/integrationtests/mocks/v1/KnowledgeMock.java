	package br.com.conhecimento.integrationtests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.conhecimento.integrationtests.vo.v1.KnowledgeVO;

public class KnowledgeMock {

	public KnowledgeVO vo() {
		return vo(0);
	}
	
	public List<KnowledgeVO> voList() {
		List<KnowledgeVO> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public KnowledgeVO vo(Integer number) {
		KnowledgeVO vo = new KnowledgeVO();
		vo.setKey(number);
		vo.setTitle("Title" + number);
		vo.setDescription("Description" + number);
		vo.setContent("Content" + number);
		vo.setSoftware(SoftwareKnwlMock.entity(number));
		
		return vo;
	}
	
}
