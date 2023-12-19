package br.com.conhecimento.services.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.conhecimento.controllers.v1.TopicController;
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
	
	@Autowired
	private PagedResourcesAssembler<TopicVO> assembler;
	
	public PagedModel<EntityModel<TopicVO>> findPageable(Pageable pageable) {
		var entityList = repository.findAll(pageable);
		
		var voList = entityList.map(t -> mapper.toVO(t));
		voList.map(t -> addLinkSelfRel(t)).toList();
		
		return assembler.toModel(voList);
	}
	
	public TopicVO findById(Integer id) {
		Topic persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public TopicVO create(TopicVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Topic createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public TopicVO updateById(Integer id, TopicVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Topic entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		
		Topic updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	public void deleteById(Integer id) {
		Topic entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private TopicVO addLinkSelfRel(TopicVO vo) {
		return vo.add(linkTo(methodOn(TopicController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private TopicVO addLinkVOList(TopicVO vo) {
		return vo.add(linkTo(methodOn(TopicController.class).findPageable(0, 10, "name", "asc")).withRel("topicVOList").expand());
	}
	
}
