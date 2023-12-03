package br.com.ibpt.services.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibpt.data.vo.v2.CompanyActiveVO;
import br.com.ibpt.data.vo.v2.CompanyVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v2.CompanyMapper;
import br.com.ibpt.model.v2.Company;
import br.com.ibpt.repositories.v2.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository repository;
	
	@Autowired
	private CompanyMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<CompanyVO> assembler;
	
	public PagedModel<EntityModel<CompanyVO>> findAll(Pageable pageable) {
		var entityList = repository.findAll(pageable);
		
		var voList = entityList.map(c -> mapper.toVO(c));
		
		return assembler.toModel(voList);
	}
	
	public CompanyVO findById(Integer id) {
		Company entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return mapper.toVO(entity);
	}
	
	public CompanyVO create(CompanyVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Company persisted = repository.save(mapper.toEntity(data));
		
		return mapper.toVO(persisted);
	}
	
	public CompanyVO updateById(Integer id, CompanyVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		Company entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setTradeName(data.getTradeName());
		entity.setBusinessName(data.getBusinessName());
		entity.setObservation(data.getObservation());
		entity.setIsActive(data.getIsActive());
		entity.setSoftwares(data.getSoftwares());
		
		Company persisted = repository.save(entity);
		
		return mapper.toVO(persisted);
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
	
}
