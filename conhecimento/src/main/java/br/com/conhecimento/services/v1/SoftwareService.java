package br.com.conhecimento.services.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.controllers.v1.SoftwareController;
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
		
		List<SoftwareVO> voList = mapper.toVOList(entityList).stream().map(s -> addLinkSelfRel(s)).toList();
		
		return voList;
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
		return vo.add(linkTo(methodOn(SoftwareController.class).findAll()).withRel("VOList").expand());
	}
	
}
