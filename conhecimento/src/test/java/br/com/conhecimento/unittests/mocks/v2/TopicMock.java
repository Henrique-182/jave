package br.com.conhecimento.unittests.mocks.v2;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.conhecimento.data.vo.v2.TopicVO;
import br.com.conhecimento.model.v2.Topic;

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
		topic.setUserLastUpdate(UserAuditMock.entity());
		topic.setLastUpdateDatetime(new Date());
		topic.setUserCreation(UserAuditMock.entity());
		topic.setCreationDatetime(new Date());
		
		return topic;
	}
	
	public TopicVO vo(Integer number) {
		TopicVO vo = new TopicVO();
		vo.setKey(number);
		vo.setName("Name" + number);
		vo.setUserLastUpdate(UserAuditMock.entity());
		vo.setLastUpdateDatetime(new Date());
		vo.setUserCreation(UserAuditMock.entity());
		vo.setCreationDatetime(new Date());
		
		return vo;
	}
	
}
