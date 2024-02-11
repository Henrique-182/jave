package br.com.mail.mappers.v1;

import org.mapstruct.Mapper;

import br.com.mail.data.vo.v1.EmailVO;
import br.com.mail.model.v1.Email;

@Mapper(componentModel = "spring")
public interface EmailMapper {

	EmailVO toVO(Email email);
	
	Email toEntity(EmailVO vo);
	
}
