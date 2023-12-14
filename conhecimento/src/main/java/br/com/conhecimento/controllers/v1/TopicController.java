package br.com.conhecimento.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.conhecimento.data.vo.v1.TopicVO;
import br.com.conhecimento.services.v1.TopicService;

@RestController
@RequestMapping(path = "/v1/topic")
public class TopicController {

	@Autowired
	private TopicService service;
	
	@GetMapping
	public List<TopicVO> findAll() {
		return service.findAll();
	}
	
}
