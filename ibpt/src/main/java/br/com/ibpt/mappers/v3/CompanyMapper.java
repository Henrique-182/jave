package br.com.ibpt.mappers.v3;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v3.CompanyVO;
import br.com.ibpt.model.v2.Company;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	@Mapping(source = "id", target = "key")
	CompanyVO toVO(Company entity);
	
	@Mapping(source = "key", target = "id")
	Company toEntity(CompanyVO vo);
	
	@Mapping(source = "id", target = "key")
	List<CompanyVO> toVOList(List<Company> entityList);
	
	@Mapping(source = "key", target = "id")
	List<Company> toEntityList(List<CompanyVO> voList);
}
