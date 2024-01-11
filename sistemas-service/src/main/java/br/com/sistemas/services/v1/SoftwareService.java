package br.com.sistemas.services.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.sistemas.controllers.v1.SoftwareController;
import br.com.sistemas.data.vo.v1.SoftwareVO;
import br.com.sistemas.exceptions.v1.RequiredObjectIsNullException;
import br.com.sistemas.exceptions.v1.ResourceNotFoundException;
import br.com.sistemas.mappers.v1.SoftwareMapper;
import br.com.sistemas.model.v1.Software;
import br.com.sistemas.repositories.v1.SoftwareCustomRepository;
import br.com.sistemas.repositories.v1.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository repository;
	
	@Autowired
	private SoftwareCustomRepository customRepository;
	
	@Autowired
	private SoftwareMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<SoftwareVO> assembler;
	
	@SuppressWarnings("unchecked")
	public PagedModel<EntityModel<SoftwareVO>> findCustomPageable(String softwareName, Pageable pageable) {
		Map<String, Object> resultMap = customRepository.findCustom(pageable, softwareName);
		
		List<SoftwareVO> voList = mapper.toVOList((List<Software>) resultMap.get("resultList"));
		
		voList = voList.stream().map(s -> addLinkSelfRel(s)).toList();
		
		long totalElements = (long) resultMap.get("totalElements");
		
		return assembler.toModel(new PageImpl<>(voList, pageable, totalElements));
	}
	
	public SoftwareVO findById(Integer id) {
		Software persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public SoftwareVO create(SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public SoftwareVO updateById(Integer id, SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		
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
