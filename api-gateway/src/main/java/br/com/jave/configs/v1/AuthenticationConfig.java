package br.com.jave.configs.v1;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AuthenticationConfig {

	private final String AUTH_SERVICE_BASE_URL = "http://localhost:8700/v1/auth/validate";

	public ResponseEntity<String> validateTokenRequest(HttpHeaders originalRequestHeader) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, originalRequestHeader.getFirst(HttpHeaders.AUTHORIZATION));

		return new RestTemplate()
				.exchange(AUTH_SERVICE_BASE_URL, HttpMethod.GET, new HttpEntity<>(headers), String.class);
	}
	
	public ResponseEntity<String> refreshTokenRequest() {
		return null;
	}
	
}
