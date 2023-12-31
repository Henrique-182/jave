package br.com.conhecimento.mappers.v2;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.conhecimento.data.vo.v2.UserVO;
import br.com.conhecimento.model.v2.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(source = "id", target = "key")
	UserVO toVO(User entity);
	
	@Mapping(source = "key", target = "id")
	User toEntity(UserVO vo);
	
}
