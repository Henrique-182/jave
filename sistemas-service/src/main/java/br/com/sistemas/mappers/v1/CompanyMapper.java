package br.com.sistemas.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sistemas.data.vo.v1.CompanyVO;
import br.com.sistemas.model.v1.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	@Mapping(source = "id", target = "key")
	CompanyVO toVO(Company entity);
	
	@Mapping(source = "id", target = "key")
	List<CompanyVO> toVOList(List<Company> entity);
	
	@Mapping(source = "key", target = "id")
	Company toEntity(CompanyVO vo);
	
}

