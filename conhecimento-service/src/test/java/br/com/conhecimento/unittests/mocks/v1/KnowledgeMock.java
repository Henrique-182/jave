package br.com.conhecimento.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.conhecimento.data.vo.v1.KnowledgeVO;
import br.com.conhecimento.model.v1.Knowledge;

public class KnowledgeMock {

	public Knowledge entity() {
		return entity(0);
	}
	
	public KnowledgeVO vo() {
		return vo(0);
	}
	
	public List<Knowledge> entityList() {
		List<Knowledge> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public List<KnowledgeVO> voList() {
		List<KnowledgeVO> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public Knowledge entity(Integer number) {
		Knowledge entity = new Knowledge();
		entity.setId(number);
		entity.setTitle("Title" + number);
		entity.setDescription("Description" + number);
		entity.setContent("Content" + number);
		entity.setSoftware(SoftwareKnwlMock.entity(number));
		entity.setTopics(TopicKnwlMock.entityList());
		
		return entity;
	}
	
	public KnowledgeVO vo(Integer number) {
		KnowledgeVO vo = new KnowledgeVO();
		vo.setKey(number);
		vo.setTitle("Title" + number);
		vo.setDescription("Description" + number);
		vo.setContent("Content" + number);
		vo.setSoftware(SoftwareKnwlMock.entity(number));
		vo.setTopics(TopicKnwlMock.entityList());
		
		return vo;
	}
	
}
