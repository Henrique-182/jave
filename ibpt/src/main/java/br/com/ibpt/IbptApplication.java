package br.com.ibpt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = "br.com.ibpt.repositories.*")
public class IbptApplication {

	public static void main(String[] args) {
		SpringApplication.run(IbptApplication.class, args);
	}

}
