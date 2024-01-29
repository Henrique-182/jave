package br.com.ibpt.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v1.IbptVO;
import br.com.ibpt.model.v1.Ibpt;

@Mapper(componentModel = "spring")
public interface IbptMapper {
	
	@Mapping(source = "id", target = "key")
	IbptVO toVO(Ibpt entity);
	
	@Mapping(source = "key", target = "id")
	Ibpt toEntity(IbptVO vo);
	
	@Mapping(source = "id", target = "key")
	List<IbptVO> toVOList(List<Ibpt> entityList);
	
	@Mapping(source = "key", target = "id")
	List<Ibpt> toEntityList(List<IbptVO> voList);
	
}
