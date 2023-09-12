package br.com.ibpt.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v1.CompanyVO;
import br.com.ibpt.model.v1.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	@Mapping(source = "id", target = "key")
	CompanyVO toCompanyVO(Company entity);
	
	@Mapping(source = "key", target = "id")
	Company toCompany(CompanyVO vo);
	
	@Mapping(source = "id", target = "key")
	List<CompanyVO> toCompanyVOList(List<Company> entityList);
	
	@Mapping(source = "key", target = "id")
	List<Company> toCompany(List<CompanyVO> voList);
}
