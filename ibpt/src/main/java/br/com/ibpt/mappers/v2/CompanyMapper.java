package br.com.ibpt.mappers.v2;

import org.mapstruct.Mapper;

import br.com.ibpt.data.vo.v2.CompanyVO;
import br.com.ibpt.model.v2.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	CompanyVO toVO(Company entity);
	
	Company toEntity(CompanyVO vo);
}
