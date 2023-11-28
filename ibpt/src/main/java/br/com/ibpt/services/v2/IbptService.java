package br.com.ibpt.services.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibpt.data.vo.v2.IbptNewVO;
import br.com.ibpt.data.vo.v2.IbptUpdateVO;
import br.com.ibpt.data.vo.v2.IbptVO;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v2.IbptMapper;
import br.com.ibpt.model.v2.Ibpt;
import br.com.ibpt.repositories.v2.IbptCustomRepository;
import br.com.ibpt.repositories.v2.IbptRepository;

@Service
public class IbptService {

	@Autowired
	private IbptRepository repository;
	
	@Autowired
	private IbptCustomRepository customRepository;
	
	@Autowired
	private IbptMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<IbptVO> assembler;
	
	public PagedModel<EntityModel<IbptVO>> findCustomPaginable(
		Pageable pageable,
		Integer id,
		String versionName,
		String companyCnpj,
		String companyName,
		Boolean isUpdated,
		String sortBy,
		String direction
	) {
		
		List<Ibpt> entityList = customRepository.findCustom(
			id, 
			versionName,
			companyCnpj,
			companyName,
			isUpdated,
			sortBy,
			direction
		);
		
		var voList = mapper.toVOList(entityList);
		
		return assembler.toModel(new PageImpl<>(voList, pageable, voList.size()));
	}
	
	public void callProcNewIbpt(IbptNewVO data) {
		repository.callProcNewIbpt(data.getKey());
	}

	@Transactional
	public void updateById(IbptUpdateVO data) {
		
		Integer idVersion = repository.findVersionById(data.getKey());
		Integer idCompanySoftware = repository.findCompanySoftwareById(data.getKey());
		
		repository.updateByVersionAndCompanySoftware(idVersion, idCompanySoftware, data.getValue());
	}
	
	public void deleteById(Integer id) {
		Ibpt entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
}
