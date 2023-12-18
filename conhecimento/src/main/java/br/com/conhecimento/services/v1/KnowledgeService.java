package br.com.conhecimento.services.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.controllers.v1.KnowledgeController;
import br.com.conhecimento.data.vo.v1.KnowledgeVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v1.KnowledgeMapper;
import br.com.conhecimento.model.v1.Knowledge;
import br.com.conhecimento.repositories.v1.KnowledgeRepository;

@Service
public class KnowledgeService {
	
	@Autowired
	private KnowledgeRepository repository;
	
	@Autowired
	private KnowledgeMapper mapper;

	public List<KnowledgeVO> findAll() {
		List<Knowledge> entityList = repository.findAll();
		
		List<KnowledgeVO> voList = mapper.toVOList(entityList).stream().map(k -> addLinkSelfRel(k)).toList();
		
		return voList;
	}
	
	public KnowledgeVO findById(Integer id) {
		Knowledge persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public KnowledgeVO create(KnowledgeVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Knowledge createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public KnowledgeVO updateById(Integer id, KnowledgeVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Knowledge entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setTitle(data.getTitle());
		entity.setDescription(data.getDescription());
		entity.setSoftware(data.getSoftware());
		entity.setContent(data.getContent());
		entity.setTopics(data.getTopics());
		
		Knowledge updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	public void deleteById(Integer id) {
		Knowledge entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private KnowledgeVO addLinkSelfRel(KnowledgeVO vo) {
		return vo.add(linkTo(methodOn(KnowledgeController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private KnowledgeVO addLinkVOList(KnowledgeVO vo) {
		return vo.add(linkTo(methodOn(KnowledgeController.class).findAll()).withRel("VOList").expand());
	}
	
}
