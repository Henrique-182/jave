package br.com.conhecimento.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.conhecimento.data.vo.v1.SoftwareVO;
import br.com.conhecimento.services.v1.SoftwareService;

@RestController
@RequestMapping(path = "/v1/software")
public class SoftwareController {

	@Autowired
	private SoftwareService service;
	
	public List<SoftwareVO> findAll() {
		return service.findAll();
	}
	
}
