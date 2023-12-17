package br.com.conhecimento.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
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
	
	public TopicVO findById(Integer id) {
		Topic persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(persistedEntity);
	}
	
	public TopicVO create(TopicVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Topic createdEntity = repository.save(mapper.toEntity(data));
		
		return mapper.toVO(createdEntity);
	}
	
	public TopicVO updateById(Integer id, TopicVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Topic entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		
		Topic updatedEntity = repository.save(entity);
		
		return mapper.toVO(updatedEntity);
	}
	
	public void deleteById(Integer id) {
		Topic entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
