package br.com.ibpt.proxys.v1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ibpt.data.vo.v1.EmailVO;

@FeignClient(name = "email-service", url = "localhost:8500")
public interface EmailProxy {

	@PostMapping(path = "/v1/email/send")
	public void sendEmail(
		@RequestBody EmailVO data,
		@RequestParam(name = "isHtml") Boolean isHtml
	);
	
}
