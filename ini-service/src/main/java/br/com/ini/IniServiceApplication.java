package br.com.ini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class IniServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IniServiceApplication.class, args);
	}

}
