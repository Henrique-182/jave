package br.com.ibpt.services.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.VersionMapper;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.repositories.v1.VersionCustomRepository;
import br.com.ibpt.repositories.v1.VersionRepository;

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
	
	public PagedModel<EntityModel<VersionVO>> findAll(
		Pageable pageable,
		String name,
		String effectivePeriodMonth,
		String effectivePeriodYear
	) {
		
		List<Version> entityList = customRepository.findCustom(name, effectivePeriodMonth, effectivePeriodYear);
		
		var voList = mapper.toVersionVOList(entityList);
		
		return assembler.toModel(new PageImpl<>(voList, pageable, voList.size()));
	}
	
	public VersionVO findById(Integer id) {
		Version entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVersionVO(entity);
	}
	
	public VersionVO create(VersionVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Version entity = mapper.toVersion(data);
		
		return mapper.toVersionVO(repository.save(entity));
	}
	
	public VersionVO updateById(Integer id, VersionVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Version entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		entity.setEffectivePeriodUntil(data.getEffectivePeriodUntil());
		
		return mapper.toVersionVO(repository.save(entity));
	}
}
