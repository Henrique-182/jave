package br.com.ibpt.mappers.v2;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ibpt.data.vo.v2.SoftwareVO;
import br.com.ibpt.model.v2.Software;

@Mapper(componentModel = "spring")
public interface SoftwareMapper {

	SoftwareVO toVO(Software entity);
	
	Software toEntity(SoftwareVO vo);
	
	List<SoftwareVO> toVOList(List<Software> entityList);
	
	List<Software> toEntityList(List<SoftwareVO> voList);
}
