package br.com.ibpt.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.CompanyVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.CompanyMapper;
import br.com.ibpt.model.v1.Company;
import br.com.ibpt.repositories.v1.CompanyCustomRepository;
import br.com.ibpt.repositories.v1.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyCustomRepository companyCustomRepository;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private IbptUpdateService ibptUpdateService;
	
	public CompanyVO findById(Integer id) {
		Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
	
		return companyMapper.toCompanyVO(company);
	}
	
	public List<CompanyVO> findCustom(
		String cnpj,
		String tradeName,
		String businessName,
		String software,
		String connection,
		Boolean haveAuthorization,
		Boolean isActive,
		Integer fkCompanySameDb
	) {
		List<Company> entityList = companyCustomRepository.findCustom(cnpj, tradeName, businessName, software, connection, haveAuthorization, isActive, fkCompanySameDb);
		
		return companyMapper.toCompanyVOList(entityList);
	}
	
	public CompanyVO create(CompanyVO vo) {
		if (vo == null) throw new RequiredObjectIsNullException();
		
		Company persisted = companyRepository.save(companyMapper.toCompany(vo));
		
		ibptUpdateService.PROC_NEW_IBPT_UPDATE();
		
		return companyMapper.toCompanyVO(persisted);
	}
	
	public CompanyVO updateById(CompanyVO vo, Integer id) {
		Company entity = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setTradeName(vo.getTradeName());
		entity.setBusinessName(vo.getBusinessName());
		entity.setSoftware(vo.getSoftware());
		entity.setHaveAuthorization(vo.getHaveAuthorization());
		entity.setConnection(vo.getConnection());
		entity.setObservation(vo.getObservation());
		entity.setIsActive(vo.getIsActive());
		entity.setFkCompanySameDb(vo.getFkCompanySameDb());
		
		return companyMapper.toCompanyVO(companyRepository.save(entity));
	}
	
}
