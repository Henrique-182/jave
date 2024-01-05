package br.com.ibpt.services.v3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v3.AccountCredentialsVO;
import br.com.ibpt.data.vo.v3.TokenVO;
import br.com.ibpt.repositories.v3.UserRepository;
import br.com.ibpt.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository userRepository;
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity signin(AccountCredentialsVO data) {
		try {
			var username = data.getUsername();
			var password = data.getPassword();
			
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);
			
			var user = userRepository.findByUsername(username);
			var tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username (" + username + ") not found!");
			} 
			
			return ResponseEntity.ok(tokenResponse);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String username, String rereshToken) {
		var user = userRepository.findByUsername(username);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(rereshToken);
		} else {
			throw new UsernameNotFoundException("Username (" + username + ") not found!");
		}
		
		return ResponseEntity.ok(tokenResponse);
	}
}
