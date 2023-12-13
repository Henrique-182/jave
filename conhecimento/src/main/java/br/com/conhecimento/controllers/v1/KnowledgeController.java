package br.com.conhecimento.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.conhecimento.data.vo.v1.KnowledgeVO;
import br.com.conhecimento.services.v1.KnowledgeService;

@RestController
@RequestMapping(path = "/v1/knowledge")
public class KnowledgeController {

	@Autowired
	private KnowledgeService service;
	
	@GetMapping
	public List<KnowledgeVO> findAll() {
		return service.findAll();
	}
	
}
