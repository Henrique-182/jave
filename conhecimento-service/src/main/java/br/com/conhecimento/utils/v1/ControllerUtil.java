package br.com.conhecimento.utils.v1;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ControllerUtil {

	public Pageable pageable(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		return PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortBy));	
	}
	
}
