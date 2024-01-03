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

import br.com.conhecimento.controllers.v2.SoftwareController;
import br.com.conhecimento.data.vo.v2.SoftwareVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v2.SoftwareMapper;
import br.com.conhecimento.model.v2.Software;
import br.com.conhecimento.model.v2.UserAudit;
import br.com.conhecimento.repositories.v1.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository repository;
	
	@Autowired
	private SoftwareMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<SoftwareVO> assembler;
	
	public PagedModel<EntityModel<SoftwareVO>> findCustomPageable(String softwareName, Pageable pageable) {
		var entityList = repository.findPageableByNameContaining(softwareName, pageable);
		
		var voList = entityList.map(s -> mapper.toVO(s));
		voList.map(s -> addLinkSelfRel(s)).toList();
		
		return assembler.toModel(voList);
	}
	
	public SoftwareVO findById(Integer id) {
		Software persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public SoftwareVO create(SoftwareVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		data.setUserLastUpdate(userAudit);
		data.setLastUpdateDatetime(new Date());
		data.setUserCreation(userAudit);
		data.setCreationDatetime(new Date());
		
		Software createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public SoftwareVO updateById(Integer id, SoftwareVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		entity.setUserLastUpdate(userAudit);
		entity.setLastUpdateDatetime(new Date());
		
		Software updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	public void deleteById(Integer id) {
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private SoftwareVO addLinkSelfRel(SoftwareVO vo) {
		return vo.add(linkTo(methodOn(SoftwareController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private SoftwareVO addLinkVOList(SoftwareVO vo) {
		return vo.add(linkTo(methodOn(SoftwareController.class).findCustomPageable(0, 10, "name", "asc", null)).withRel("softwareVOList").expand());
	}
	
}
