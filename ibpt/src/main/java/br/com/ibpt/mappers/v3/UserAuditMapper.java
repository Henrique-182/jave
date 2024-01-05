package br.com.ibpt.mappers.v3;

import org.mapstruct.Mapper;

import br.com.ibpt.model.v3.User;
import br.com.ibpt.model.v3.UserAudit;

@Mapper(componentModel = "spring")
public interface UserAuditMapper {

	UserAudit toAudit(User entity);
	
}
