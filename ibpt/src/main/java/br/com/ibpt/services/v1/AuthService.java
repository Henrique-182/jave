package br.com.ibpt.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.AccountCredentialsVO;
import br.com.ibpt.data.vo.v1.TokenVO;
import br.com.ibpt.repositories.v1.UserRepository;
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
			var userName = data.getUserName();
			var password = data.getPassword();
			
			authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userName, password)
			);
			
			var user = userRepository.findByUserName(userName);
			var tokenResponse = new TokenVO();
			
			if (user != null) {
				tokenResponse = tokenProvider.createAccessToken(userName, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username (" + userName + ") not found!");
			} 
			
			return ResponseEntity.ok(tokenResponse);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public ResponseEntity refreshToken(String userName, String rereshToken) {
		var user = userRepository.findByUserName(userName);
		
		var tokenResponse = new TokenVO();
		if (user != null) {
			tokenResponse = tokenProvider.refreshToken(rereshToken);
		} else {
			throw new UsernameNotFoundException("Username (" + userName + ") not found!");
		}
		
		return ResponseEntity.ok(tokenResponse);
	}
}
