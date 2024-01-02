package br.com.conhecimento.services.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.model.v2.UserAudit;
import br.com.conhecimento.repositories.v2.UserAuditRepository;

@Service
public class UserAuditService {

	@Autowired
	private UserAuditRepository repository;
	
	public UserAudit findByUsername(String username) {
		return repository.findByUsername(username);
	}
	
}
