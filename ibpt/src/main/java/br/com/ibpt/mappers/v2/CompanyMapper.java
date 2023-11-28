package br.com.ibpt.mappers.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v2.CompanyVO;
import br.com.ibpt.model.v2.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	@Mapping(source = "id", target = "key")
	CompanyVO toVO(Company entity);
	
	@Mapping(source = "key", target = "id")
	Company toEntity(CompanyVO vo);
}
