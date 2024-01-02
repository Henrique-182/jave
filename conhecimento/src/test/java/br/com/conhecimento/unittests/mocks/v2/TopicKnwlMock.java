package br.com.conhecimento.unittests.mocks.v2;

import java.util.ArrayList;
import java.util.List;

import br.com.conhecimento.model.v2.TopicKnwl;

class TopicKnwlMock {
	
	static List<TopicKnwl> entityList() {
		List<TopicKnwl> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}

	static TopicKnwl entity(Integer number) {
		TopicKnwl entity = new TopicKnwl();
		entity.setId(number);
		entity.setName("Name" + number);
		
		return entity;
	}
	
}
