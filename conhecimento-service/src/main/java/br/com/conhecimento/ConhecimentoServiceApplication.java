package br.com.conhecimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients	
@ComponentScan(basePackages = "br.com.conhecimento.repositories.*")
public class ConhecimentoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConhecimentoServiceApplication.class, args);
	}

}
