package br.com.ibpt.mappers.v1;

import java.util.List;

import org.mapstruct.Mapper;

import br.com.ibpt.data.vo.v1.UserVO;
import br.com.ibpt.model.v1.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserVO toUserVO(User entity);

	List<UserVO> toUserVOList(List<User> entityList);
}
