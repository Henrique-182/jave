package br.com.conhecimento.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.conhecimento.data.vo.v1.KnowledgeVO;
import br.com.conhecimento.mappers.v1.KnowledgeMapper;
import br.com.conhecimento.model.v1.Knowledge;
import br.com.conhecimento.repositories.v1.KnowledgeRepository;

@Service
public class KnowledgeService {
	
	@Autowired
	private KnowledgeRepository repository;
	
	@Autowired
	private KnowledgeMapper mapper;

	public List<KnowledgeVO> findAll() {
		List<Knowledge> entityList = repository.findAll();
		
		return mapper.toVOList(entityList);
	}
	
}
