package br.com.ibpt.mappers.v2;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v3.SoftwareVO;
import br.com.ibpt.model.v2.Software;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {

	@Mapping(source = "id", target = "key")
	SoftwareVO toVO(Software entity);
	
	@Mapping(source = "key", target = "id")
	Software toEntity(SoftwareVO vo);
	
	@Mapping(source = "id", target = "key")
	List<SoftwareVO> toVOList(List<Software> entityList);
	
	@Mapping(source = "key", target = "id")
	List<Software> toEntityList(List<SoftwareVO> voList);
}
