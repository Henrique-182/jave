package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.AccountCredentialsVO;
import br.com.ibpt.data.vo.v1.UserVO;
import br.com.ibpt.services.v1.UserService;

@RestController
@RequestMapping("/v1/usuario")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public UserVO findAll(@PathVariable("id") Integer id) {
		return userService.findById(id);
	}
	
	@GetMapping
	public List<UserVO> findAll() {
		return userService.findAll();
	}
	
	@PostMapping("/novo")
	public UserVO create(@RequestBody AccountCredentialsVO vo) {
		 return userService.create(vo);
	}
	
	@PutMapping("/{id}")
	public UserVO update(@PathVariable("id") Integer id, @RequestBody UserVO vo) {
		return userService.updateById(id, vo);
	}
}
