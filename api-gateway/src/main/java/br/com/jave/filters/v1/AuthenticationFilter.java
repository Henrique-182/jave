package br.com.jave.filters.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import br.com.jave.configs.v1.AuthenticationConfig;
import br.com.jave.exceptions.v1.InvalidJwtAuthenticationException;
import br.com.jave.services.v1.RouteValidator;

@Component
public class AuthenticationFilter implements GatewayFilterFactory<AuthenticationConfig> {
	
	@Autowired
	private RouteValidator validator;
	
	@Value("${security.defaultZone}")
	private String defaultZone;
	
	public AuthenticationFilter() {
		super();
	}
	
	@Override
    public Class<AuthenticationConfig> getConfigClass() {
        return AuthenticationConfig.class;
    }

	public GatewayFilter apply(AuthenticationConfig config) {

		return ((exchange, chain) -> {
			 
			ServerHttpRequest request = exchange.getRequest();
				
			if (validator.isSecured.test(request)) {
				if (!request.getHeaders().containsKey("Authorization")) {
					throw new InvalidJwtAuthenticationException("No Header Authorization Found!");
				}
				
				try {
					config.validateTokenRequest(defaultZone, request.getHeaders());
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
					throw new InvalidJwtAuthenticationException("Supplied Token is Invalid!");
				}
		        
			}
				
	        return chain.filter(exchange);
	    });
	}

}