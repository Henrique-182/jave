package br.com.conhecimento.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.data.vo.v1.SoftwareVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v1.SoftwareMapper;
import br.com.conhecimento.model.v1.Software;
import br.com.conhecimento.repositories.v1.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository repository;
	
	@Autowired
	private SoftwareMapper mapper;
	
	public List<SoftwareVO> findAll() {
		List<Software> entityList = repository.findAll();
		
		return mapper.toVOList(entityList);
	}
	
	public SoftwareVO findById(Integer id) {
		Software persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(persistedEntity);
	}
	
	public SoftwareVO create(SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software createdEntity = repository.save(mapper.toEntity(data));
		
		return mapper.toVO(createdEntity);
	}
	
	public SoftwareVO updateById(Integer id, SoftwareVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setName(data.getName());
		
		Software updatedEntity = repository.save(entity);
		
		return mapper.toVO(updatedEntity);
	}
	
	public void deleteById(Integer id) {
		Software entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
