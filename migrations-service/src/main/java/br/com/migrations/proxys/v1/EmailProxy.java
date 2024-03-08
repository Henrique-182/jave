package br.com.migrations.proxys.v1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.migrations.data.vo.v1.EmailVO;
import br.com.migrations.model.v1.Email;

@FeignClient(name = "email-service")
public interface EmailProxy {

	@PostMapping(path = "/v1/email/send")
	public Email getEmail(
		@RequestBody EmailVO data,
		@RequestParam(name = "isHtml") Boolean isHtml
	);
	
}
