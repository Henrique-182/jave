package br.com.ibpt.services.v3;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibpt.controllers.v3.IbptController;
import br.com.ibpt.data.vo.v2.IbptUpdateVO;
import br.com.ibpt.data.vo.v3.IbptVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v3.IbptMapper;
import br.com.ibpt.model.v3.Ibpt;
import br.com.ibpt.model.v3.UserAudit;
import br.com.ibpt.repositories.v3.IbptCustomRepository;
import br.com.ibpt.repositories.v3.IbptRepository;

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
		String versionName,
		String companyCnpj,
		String companyName,
		Boolean isUpdated
	) {
		
		List<Ibpt> entityList = customRepository.findCustom(
			pageable,
			versionName,
			companyCnpj,
			companyName,
			isUpdated
		);
		var voList = mapper.toVOList(entityList);
		
		voList = voList.stream().map(i -> addLinkSelfRel(i)).toList();
		
		long totalElements = repository.count();
		
		return assembler.toModel(new PageImpl<>(voList, pageable, totalElements));
	}
	
	public IbptVO findById(Integer id) {
		Ibpt persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public void callProcNewIbpt(Integer versionId) {
		repository.callProcNewIbpt(versionId);
	}

	@Transactional
	public void updateById(IbptUpdateVO data, UserAudit userAudit) {
		if (data == null) throw new RequiredObjectIsNullException("It is not possible to update a null object");
		
		Ibpt persistedEntity = repository.findById(data.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + data.getKey() + ") !"));
		
		Integer idVersion = persistedEntity.getVersion().getId();
		Integer idCompanySoftware = persistedEntity.getCompanySoftware().getId();
		
		repository.updateByVersionAndCompanySoftware(idVersion, idCompanySoftware, data.getValue(), userAudit, new Date());
	}
	
	public void deleteById(Integer id) {
		Ibpt entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private IbptVO addLinkSelfRel(IbptVO vo) {
		return vo.add(linkTo(methodOn(IbptController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private IbptVO addLinkVOList(IbptVO vo) {
		return vo.add(linkTo(methodOn(IbptController.class).findCustomPageable(0, 10, "asc", "id", null, null, null, null)).withRel("ibptVOList").expand());
	}
}
