package br.com.jave.mappers.v1;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.jave.data.vo.v1.UserVO;
import br.com.jave.model.v1.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	@Mapping(source = "id", target = "key")
	UserVO toVO(User entity);
	
	@Mapping(source = "key", target = "id")
	User toEntity(UserVO vo);

}
