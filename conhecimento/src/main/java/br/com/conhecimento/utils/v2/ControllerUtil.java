package br.com.conhecimento.utils.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import br.com.conhecimento.model.v2.UserAudit;
import br.com.conhecimento.services.v2.UserAuditService;

@Service
public class ControllerUtil {
	
	@Autowired
	private UserAuditService userAuditService;
	
	public Pageable pageable(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		return PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortBy));	
	}
	
	public UserAudit getUserByContext(SecurityContext context) {
		return userAuditService.findByUsername(context.getAuthentication().getName());
	}
	
}
