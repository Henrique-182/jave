package br.com.sistemas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SistemasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemasApplication.class, args);
	}

}
