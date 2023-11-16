package br.com.ibpt.mappers.v2;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ibpt.data.vo.v2.IbptVO;
import br.com.ibpt.model.v2.Ibpt;

@Mapper(componentModel = "spring")
public interface IbptMapper {

	IbptVO toVO(Ibpt entity);
	
	Ibpt toEntity(IbptVO vo);
	
	List<IbptVO> toVOList(List<Ibpt> entityList);
	
	List<Ibpt> toEntityList(List<IbptVO> voList);
}
