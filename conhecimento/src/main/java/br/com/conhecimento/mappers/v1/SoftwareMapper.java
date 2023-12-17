package br.com.conhecimento.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.conhecimento.data.vo.v1.SoftwareVO;
import br.com.conhecimento.model.v1.Software;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {

	@Mapping(source = "id", target = "key")
	SoftwareVO toVO(Software entity);
	
	@Mapping(source = "key", target = "id")
	Software toEntity(SoftwareVO vo);
	
	@Mapping(source = "id", target = "key")
	List<SoftwareVO> toVOList(List<Software> entity);
	
	@Mapping(source = "key", target = "id")
	List<Software> toEntity(List<SoftwareVO> vo);
	
}
