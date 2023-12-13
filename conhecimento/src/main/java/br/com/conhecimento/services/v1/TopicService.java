package br.com.conhecimento.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.mappers.v1.TopicMapper;
import br.com.conhecimento.model.v1.Topic;
import br.com.conhecimento.repositories.v1.TopicRepository;

@Service
public class TopicService {

	@Autowired
	private TopicRepository repository;
	
	@Autowired
	private TopicMapper mapper;
	
	public List<TopicVO> findAll() {
		List<Topic> entityList = repository.findAll();
		
		return mapper.toVOList(entityList);
	}
	
}
