package br.com.ibpt.controllers.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibpt.data.vo.v1.CompanyVO;
import br.com.ibpt.services.v1.CompanyService;

@RestController
@RequestMapping(path = "/v1/empresa")
public class CompanyController {

	@Autowired
	private CompanyService companyService;
	
	
	@GetMapping(path = "/{id}")
	public CompanyVO findById(@PathVariable("id") Integer id) {
		return companyService.findById(id);
	}
	
	@GetMapping
	public List<CompanyVO> findCustom(
		@RequestParam(value = "cnpj", required = false) String cnpj,
		@RequestParam(value = "nome", required = false) String tradeName,
		@RequestParam(value = "razaoSocial", required = false) String businessName,
		@RequestParam(value = "sistema", required = false) String software,
		@RequestParam(value = "conexao", required = false) String connection,
		@RequestParam(value = "temAutorizacao", required = false) Boolean haveAuthorization,
		@RequestParam(value = "estaAtivo", required = false) Boolean isActive,
		@RequestParam(value = "fkEmpresaMesmoBd", required = false) Integer fkCompanySameDb
	) {
		return companyService.findCustom(cnpj, tradeName, businessName, software, connection, haveAuthorization, isActive, fkCompanySameDb);
	}
	
	@PostMapping(
		path = "/novo",
		consumes = {MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	public CompanyVO create(@RequestBody CompanyVO vo) {
		return companyService.create(vo);
	}
	
	@PutMapping(path = "/{id}")
	public CompanyVO updateById(@RequestBody CompanyVO vo, @PathVariable("id") Integer id) {
		return companyService.updateById(vo, id);
	}
	
}
