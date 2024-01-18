package br.com.ibpt.utils.v1;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ControllerUtil {
	
	public Pageable pageable(Integer page, Integer size, String sortBy, String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		return PageRequest.of(page, size, Sort.by(sortDirection, sortBy));		
	}
	
	public Pageable pageable(Integer page, Integer size) {
		
		return PageRequest.of(page, size);		
	}
	
}
