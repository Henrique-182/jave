package br.com.ibpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IbptServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbptServiceApplication.class, args);
	}

}
