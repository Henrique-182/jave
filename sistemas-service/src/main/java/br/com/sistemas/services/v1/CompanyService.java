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
import org.springframework.transaction.annotation.Transactional;

import br.com.sistemas.controllers.v1.CompanyController;
import br.com.sistemas.data.vo.v1.CompanyActiveVO;
import br.com.sistemas.data.vo.v1.CompanyVO;
import br.com.sistemas.exceptions.v1.RequiredObjectIsNullException;
import br.com.sistemas.exceptions.v1.ResourceNotFoundException;
import br.com.sistemas.mappers.v1.CompanyMapper;
import br.com.sistemas.model.v1.Company;
import br.com.sistemas.repositories.v1.CompanyCustomRepository;
import br.com.sistemas.repositories.v1.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repository;
	
	@Autowired
	private CompanyCustomRepository customRepository;
	
	@Autowired
	private CompanyMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<CompanyVO> assembler;
	
	@SuppressWarnings("unchecked")
	public PagedModel<EntityModel<CompanyVO>> findPageable(Pageable pageable, Map<String, Object> params) {
		Map<String, Object> resultMap = customRepository.findCustom(pageable, params);
		
		List<CompanyVO> voList = mapper.toVOList((List<Company>) resultMap.get("resultList"));
		
		voList = voList.stream().map(c -> addLinkSelfRel(c)).toList();
		
		long totalElements = (long) resultMap.get("totalElements");
		
		return assembler.toModel(new PageImpl<>(voList, pageable, totalElements));
	}
	
	public CompanyVO findById(Integer id) {
		Company entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(entity));
	}
	
	public CompanyVO create(CompanyVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Company createdEntity = repository.save(mapper.toEntity(data));
		
		return addLinkVOList(mapper.toVO(createdEntity));
	}
	
	public CompanyVO updateById(Integer id, CompanyVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Company entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setTradeName(data.getTradeName());
		entity.setBusinessName(data.getBusinessName());
		entity.setObservation(data.getObservation());
		entity.setIsActive(data.getIsActive());
		entity.setSoftwares(data.getSoftwares());
		
		Company updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	@Transactional
	public void updateCompanyIsActiveById(CompanyActiveVO data) {
		
		repository.updateCompanyIsActiveById(data.getKey(), data.getValue());
		
		repository.updateCompanySoftwareIsActiveByFkCompany(data.getKey(), data.getValue());
	}
	
	@Transactional
	public void updateCompanySoftwareIsActiveById(CompanyActiveVO data) {
		
		repository.updateCompanySoftwareIsActiveById(data.getKey(), data.getValue());
	}
	
	public void deleteById(Integer id) {
		Company entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private CompanyVO addLinkSelfRel(CompanyVO vo) {
		return vo.add(linkTo(methodOn(CompanyController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private CompanyVO addLinkVOList(CompanyVO vo) {
		return vo.add(linkTo(methodOn(CompanyController.class).findPageable(0, 10, "asc", "name", null, null, null, null, null, null)).withRel("companyVOList").expand());
	}
	
}
