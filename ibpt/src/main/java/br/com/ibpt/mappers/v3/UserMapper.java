package br.com.ibpt.mappers.v3;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.ibpt.data.vo.v3.UserVO;
import br.com.ibpt.model.v3.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(source = "id", target = "key")
	UserVO toVO(User entity);
	
	@Mapping(source = "id", target = "key")
	List<UserVO> toVOList(List<User> entityList);
	
}
