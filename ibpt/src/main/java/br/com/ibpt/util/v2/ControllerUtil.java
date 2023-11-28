package br.com.ibpt.util.v2;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class ControllerUtil {

	public static Pageable pageable(Integer page, Integer size, String direction, String sortBy) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		return PageRequest.of(page, size, Sort.by(sortDirection, sortBy));		
	}
	
	public static Pageable pageable(Integer page, Integer size) {
		
		return PageRequest.of(page, size);		
	}
}
