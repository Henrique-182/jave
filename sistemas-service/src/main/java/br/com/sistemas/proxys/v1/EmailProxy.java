package br.com.sistemas.proxys.v1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.sistemas.data.vo.v1.EmailVO;
import br.com.sistemas.model.v1.Email;

@FeignClient(name = "email-service", url = "localhost:8585")
public interface EmailProxy {

	@PostMapping(path = "/v1/email/send")
	public Email sendEmail(
		@RequestBody EmailVO data,
		@RequestParam(name = "isHtml") Boolean isHtml
	);
	
}
