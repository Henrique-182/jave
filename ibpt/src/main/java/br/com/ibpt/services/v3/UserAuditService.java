package br.com.ibpt.services.v3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibpt.mappers.v3.UserAuditMapper;
import br.com.ibpt.model.v3.UserAudit;
import br.com.ibpt.repositories.v3.UserRepository;

@Service
public class UserAuditService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserAuditMapper mapper;
	
	
	public UserAudit findByUsername(String username) {
		
		return mapper.toAudit(repository.findByUsername(username));
	}
	
}
