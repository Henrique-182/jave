package br.com.conhecimento.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.data.vo.v1.SoftwareVO;
import br.com.conhecimento.mappers.v1.SoftwareMapper;
import br.com.conhecimento.repositories.v1.SoftwareRepository;

@Service
public class SoftwareService {

	@Autowired
	private SoftwareRepository repository;
	
	@Autowired
	private SoftwareMapper mapper;
	
	public List<SoftwareVO> findAll() {
		return repository.findAll().stream().map(s -> mapper.toVO(s)).toList();
	}
}
