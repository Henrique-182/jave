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
import org.springframework.transaction.annotation.Transactional;

import br.com.ibpt.controllers.v3.CompanyController;
import br.com.ibpt.data.vo.v2.CompanyActiveVO;
import br.com.ibpt.data.vo.v3.CompanyVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v3.CompanyMapper;
import br.com.ibpt.model.v2.Company;
import br.com.ibpt.repositories.v2.CompanyRepository;
import br.com.ibpt.repositories.v3.CompanyCustomRepository;

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
	
	public PagedModel<EntityModel<CompanyVO>> findPageable(
		Pageable pageable,
		String cnpj,
		String name,
		Boolean isActive,
		String softwareName,
		String softwareType,
		Integer softwareFkSameDb,
		String sortBy,
		String direction
	) {
		List<Company> entityList = customRepository.findCustom(
				cnpj,
				name,
				isActive,
				softwareName,
				softwareType,
				softwareFkSameDb,
				sortBy,
				direction
		);
		
		List<CompanyVO> voList = mapper.toVOList(entityList);
		
		voList = voList.stream().map(c -> addLinkSelfRel(c)).toList();
		
		final int start = (int) pageable.getOffset();
		final int end = Math.min((start + pageable.getPageSize()), voList.size());
		
		return assembler.toModel(new PageImpl<>(voList.subList(start, end), pageable, voList.size()));
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
		
		repository.updateCompanyIsActiveById(data.getId(), data.getValue());
		
		repository.updateCompanySoftwareIsActiveByFkCompany(data.getId(), data.getValue());
	}
	
	@Transactional
	public void updateCompanySoftwareIsActiveById(CompanyActiveVO data) {
		
		repository.updateCompanySoftwareIsActiveById(data.getId(), data.getValue());
	}
	
	public void deleteById(Integer id) {
		Company entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private CompanyVO addLinkSelfRel(CompanyVO vo) {
		return vo.add(linkTo(methodOn(CompanyController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private CompanyVO addLinkVOList(CompanyVO vo) {
		return vo.add(linkTo(methodOn(CompanyController.class).findPageable(0, 10, "asc", "name", null, null, null, null, null, null)).withRel("companyVOList").expand());
	}
	
}
