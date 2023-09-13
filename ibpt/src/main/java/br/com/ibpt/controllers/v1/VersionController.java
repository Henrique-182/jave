package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.services.v1.VersionService;

@RestController
@RequestMapping(path = "/v1/versao")
public class VersionController {
	
	@Autowired
	private VersionService versionService; 

	@GetMapping
	public List<VersionVO> findAll(
			@RequestParam(value = "nome", required = false) String name,
			@RequestParam(value = "mesVigencia", required = false) String effectivePeriodMonth,
			@RequestParam(value = "anoVigencia", required = false) String effectivePeriodYear
		) {
		return versionService.findCustom(name, effectivePeriodMonth, effectivePeriodYear);
	}
	
	@PostMapping(path = "/novo")
	public VersionVO create(@RequestBody VersionVO vo) {
		return versionService.create(vo);
	}
	
	@PutMapping(path = "/{id}")
	public VersionVO update(@RequestBody VersionVO vo, @PathVariable("id") Integer id) {
		return versionService.update(vo, id);
	}
}
