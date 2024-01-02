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

import br.com.conhecimento.controllers.v2.KnowledgeController;
import br.com.conhecimento.data.vo.v2.KnowledgeVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v2.KnowledgeMapper;
import br.com.conhecimento.model.v2.Knowledge;
import br.com.conhecimento.model.v2.UserAudit;
import br.com.conhecimento.repositories.v2.KnowledgeRepository;

@Service
public class KnowledgeService {
	
	@Autowired
	private KnowledgeRepository repository;
	
	@Autowired
	private KnowledgeMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<KnowledgeVO> assembler;

	public PagedModel<EntityModel<KnowledgeVO>> findCustomPageable(
		String knowledgeTitle, 
		String knowledgeDescription, 
		String knowledgeContent,
		Pageable pageable
	) {
		var entityList = repository.findPageableByTitleContainingAndDescriptionContainingAndContentContaining(
				knowledgeTitle, 
				knowledgeDescription, 
				knowledgeContent, 
				pageable
			);
		
		var voList = entityList.map(k -> mapper.toVO(k));
		voList.map(k -> addLinkSelfRel(k));
		
		return assembler.toModel(voList);
	}
	
	public KnowledgeVO findById(Integer id) {
		Knowledge persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public KnowledgeVO create(KnowledgeVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		data.setUserLastUpdate(userAudit);
		data.setLastUpdateDatetime(new Date());
		data.setUserCreation(userAudit);
		data.setCreationDatetime(new Date());
		
		Knowledge createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public KnowledgeVO updateById(Integer id, KnowledgeVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Knowledge entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setTitle(data.getTitle());
		entity.setDescription(data.getDescription());
		entity.setSoftware(data.getSoftware());
		entity.setContent(data.getContent());
		entity.setUserLastUpdate(userAudit);
		entity.setLastUpdateDatetime(new Date());
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
		return vo.add(linkTo(methodOn(KnowledgeController.class).findCustomPageable(0, 10, "title", "asc", null, null, null)).withRel("knowledgeVOList").expand());
	}
	
}
