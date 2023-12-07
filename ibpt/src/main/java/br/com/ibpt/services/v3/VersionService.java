package br.com.ibpt.services.v3;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.ibpt.controllers.v3.VersionController;
import br.com.ibpt.data.vo.v3.VersionVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v3.VersionMapper;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.repositories.v1.VersionRepository;
import br.com.ibpt.repositories.v2.VersionCustomRepository;

@Service
public class VersionService {

	@Autowired
	private VersionRepository repository;
	
	@Autowired
	private VersionCustomRepository customRepository;
	
	@Autowired
	private VersionMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<VersionVO> assembler;
	
	public PagedModel<EntityModel<VersionVO>> findCustomPaginable(
		Pageable pageable,
		String name,
		String effectivePeriodMonth,
		String effectivePeriodYear,
		String sortBy,
		String direction
	) {
		
		List<Version> entityList = customRepository.findCustom(
				name, 
				effectivePeriodMonth, 
				effectivePeriodYear, 
				sortBy, 
				direction
		);
		
		var voList = mapper.toVOList(entityList);
		
		voList = voList.stream().map(v -> addLinkSelfRef(v)).toList();
		
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), voList.size());
		
		return assembler.toModel(new PageImpl<>(voList.subList(start, end), pageable, voList.size()));
	}
	
	public VersionVO findById(Integer id) {
		Version entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(entity));
	}
	
	public VersionVO create(VersionVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Version createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public VersionVO updateById(Integer id, VersionVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Version entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		entity.setEffectivePeriodUntil(data.getEffectivePeriodUntil());
		
		Version updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	public void deleteById(Integer id) {
		Version entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private VersionVO addLinkSelfRef(VersionVO vo) {
		return vo.add(linkTo(methodOn(VersionController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private VersionVO addLinkVOList(VersionVO vo) {
		return vo.add(linkTo(methodOn(VersionController.class).findCustomPaginable(0, 10, "asc", "name", null, null, null)).withRel("versionVOList").expand());
	}
	
}
