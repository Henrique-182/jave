package br.com.jave.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jave.data.vo.v1.AccountCredentialsVO;
import br.com.jave.data.vo.v1.TokenVO;
import br.com.jave.repositories.v1.UserRepository;
import br.com.jave.security.jwt.v1.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);
			
			var user = repository.findByUsername(username);
			var tokenResponse = new TokenVO();
			
			if (user != null) tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			else throw new UsernameNotFoundException("Username (" + username + ") not found!");
			
			return ResponseEntity.ok(tokenResponse);
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	public ResponseEntity<?> refresh(String username, String refreshToken) {
		var user = repository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		
		if (user != null) tokenResponse = tokenProvider.refreshToken(refreshToken);
		else throw new UsernameNotFoundException("Username (" + username + ") not found!");
		
		return ResponseEntity.ok(tokenResponse);
	}
	
	public Boolean validate(HttpServletRequest req) {
		String token = tokenProvider.resolveToken(req);
		
		return tokenProvider.validateToken(token);
	}

}
