package br.com.ibpt.util.v3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import br.com.ibpt.model.v3.UserAudit;
import br.com.ibpt.services.v3.UserAuditService;

@Service
public class ControllerUtil {
	
	@Autowired
	private UserAuditService service;

	public Pageable pageable(Integer page, Integer size, String direction, String sortBy) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		return PageRequest.of(page, size, Sort.by(sortDirection, sortBy));		
	}
	
	public Pageable pageable(Integer page, Integer size) {
		
		return PageRequest.of(page, size);		
	}
	
	public UserAudit findUserByContext(SecurityContext context) {
		
		return service.findByUsername(context.getAuthentication().getName());
	}
	
}
