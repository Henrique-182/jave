package br.com.ibpt.services.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v2.SoftwareVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
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
	
	public PagedModel<EntityModel<SoftwareVO>> findAll(Pageable pageable) {
		var entityList = repository.findAll(pageable);
		
		var voList = entityList.map(s -> mapper.toVO(s));
		
		return assembler.toModel(voList);
	}
	
	public SoftwareVO findById(Integer id) {
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(entity);
	}
	
	public SoftwareVO create(SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = mapper.toEntity(data);
		
		return mapper.toVO(repository.save(entity));
	}
	
	public SoftwareVO updateById(Integer id, SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		
		return mapper.toVO(repository.save(entity));
	}
	
	public void deleteById(Integer id) {
		Software entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
