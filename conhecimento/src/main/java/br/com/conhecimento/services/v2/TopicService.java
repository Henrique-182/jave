package br.com.conhecimento.services.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.conhecimento.controllers.v2.TopicController;
import br.com.conhecimento.data.vo.v2.TopicVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v2.TopicMapper;
import br.com.conhecimento.model.v2.Topic;
import br.com.conhecimento.model.v2.UserAudit;
import br.com.conhecimento.repositories.v2.TopicRepository;

@Service
public class TopicService {

	@Autowired
	private TopicRepository repository;
	
	@Autowired
	private TopicMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<TopicVO> assembler;
	
	public PagedModel<EntityModel<TopicVO>> findCustomPageable(String topicName, Pageable pageable) {
		var entityList = repository.findPageableByNameContaining(topicName, pageable);
		
		var voList = entityList.map(t -> mapper.toVO(t));
		voList.map(t -> addLinkSelfRel(t)).toList();
		
		return assembler.toModel(voList);
	}
	
	public TopicVO findById(Integer id) {
		Topic persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public TopicVO create(TopicVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		data.setUserLastUpdate(userAudit);
		data.setLastUpdateDatetime(new Date());
		data.setUserCreation(userAudit);
		data.setCreationDatetime(new Date());
		
		Topic createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public TopicVO updateById(Integer id, TopicVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Topic entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		entity.setUserLastUpdate(userAudit);
		entity.setLastUpdateDatetime(new Date());
		
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
		return vo.add(linkTo(methodOn(TopicController.class).findCustomPageable(0, 10, "name", "asc", null)).withRel("topicVOList").expand());
	}
	
}
