package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.IbptUpdateVO;
import br.com.ibpt.data.vo.v1.UpdateVO;
import br.com.ibpt.services.v1.IbptUpdateService;

@RestController
@RequestMapping("/v1/atualizacao")
public class IbptUpdateController {

	@Autowired
	private IbptUpdateService ibptUpdateService;
	
	@GetMapping
	public List<IbptUpdateVO> findCustom(
		@RequestParam(value = "versaoNome", required = false) String versionName,
		@RequestParam(value = "empresaCnpj", required = false) String companyCnpj,
		@RequestParam(value = "empresaNome", required = false) String companyBusinessName,
		@RequestParam(value = "empresaRazao", required = false) String companyTradeName
	) {
		return ibptUpdateService.findCustom(versionName, companyCnpj, companyBusinessName, companyTradeName);
	}
	
	@PutMapping
	public void updateById(@RequestBody UpdateVO vo) {
		ibptUpdateService.updateById(vo.getId());
	}
	
}
