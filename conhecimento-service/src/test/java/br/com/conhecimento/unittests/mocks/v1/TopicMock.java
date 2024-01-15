package br.com.conhecimento.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.model.v1.Topic;

public class TopicMock {

	public Topic entity() {
		return entity(0);
	}
	
	public TopicVO vo() {
		return vo(0);
	}
	
	public List<Topic> entityList() {
		List<Topic> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public List<TopicVO> voList() {
		List<TopicVO> list = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public Topic entity(Integer number) {
		Topic topic = new Topic();
		topic.setId(number);
		topic.setName("Name" + number);
		
		return topic;
	}
	
	public TopicVO vo(Integer number) {
		TopicVO vo = new TopicVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		
		return vo;
	}
	
}
