package br.com.conhecimento.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/controller")
public class Controller {
	
	@GetMapping
	public String controller() {
		return "Controller";
	}

}
