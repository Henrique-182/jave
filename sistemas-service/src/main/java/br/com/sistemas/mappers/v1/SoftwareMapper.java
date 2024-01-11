package br.com.sistemas.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.sistemas.data.vo.v1.SoftwareVO;
import br.com.sistemas.model.v1.Software;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {

	@Mapping(source = "id", target = "key")
	SoftwareVO toVO(Software entity);
	
	@Mapping(source = "key", target = "id")
	Software toEntity(SoftwareVO vo);
	
	@Mapping(source = "id", target = "id")
	List<SoftwareVO> toVOList(List<Software> entityList);
	
}
