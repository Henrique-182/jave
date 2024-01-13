package br.com.conhecimento.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.model.v1.Topic;

@Mapper(componentModel = "spring")
public interface TopicMapper {
	

	@Mapping(source = "id", target = "key")
	TopicVO toVO(Topic entity);
	
	@Mapping(source = "id", target = "key")
	List<TopicVO> toVOList(List<Topic> entityList);
	
	@Mapping(source = "key", target = "id")
	Topic toEntity(TopicVO vo);
	
	@Mapping(source = "key", target = "id")
	List<Topic> toEntityList(List<TopicVO> voList);
	
}
