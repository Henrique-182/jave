package br.com.ibpt.services.v3;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.ibpt.controllers.v3.SoftwareController;
import br.com.ibpt.data.vo.v3.SoftwareVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v2.SoftwareMapper;
import br.com.ibpt.model.v2.Software;
import br.com.ibpt.repositories.v2.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository repository;
	
	@Autowired
	private PagedResourcesAssembler<SoftwareVO> assembler;
	
	@Autowired
	private SoftwareMapper mapper;
	
	public PagedModel<EntityModel<SoftwareVO>> findAllPageable(Pageable pageable) {
		var entityList = repository.findAll(pageable);
		
		var voList = entityList.map(s -> mapper.toVO(s));
		voList.map(s -> addLinkSelfRel(s));
		
		return assembler.toModel(voList);
	}
	
	public SoftwareVO findById(Integer id) {
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(entity));
	}
	
	public SoftwareVO create(SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(entity));
	}
	
	public SoftwareVO updateById(Integer id, SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		
		Software persistedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public void deleteById(Integer id) {
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private SoftwareVO addLinkSelfRel(SoftwareVO vo) {
		return vo.add(linkTo(methodOn(SoftwareController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private SoftwareVO addLinkVOList(SoftwareVO vo) {
		return vo.add(linkTo(methodOn(SoftwareController.class).findAllPageable(0, 10, "asc")).withRel("softwareVOList"));
	}
	
}
