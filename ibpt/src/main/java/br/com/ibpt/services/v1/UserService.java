package br.com.ibpt.services.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.AccountCredentialsVO;
import br.com.ibpt.data.vo.v1.UserVO;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.UserMapper;
import br.com.ibpt.model.v1.Permission;
import br.com.ibpt.model.v1.User;
import br.com.ibpt.repositories.v1.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userRepository.findByUserName(username);
		
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username (" + username + ") not found!");
		}
	}
	
	public UserVO findById(Integer id) {
		User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		
		return userMapper.toUserVO(entity);
	}
	
	public List<UserVO> findAll() {
		return userMapper.toUserVOList(userRepository.findAll());
	}
	
	public UserVO create(AccountCredentialsVO vo) {
		User user = new User();
		user.setUserName(vo.getUserName());
		user.setFullName(vo.getFullname());
		
		String password = createHash(vo.getPassword());
		if (password.startsWith("{pbkdf2}")) {
			password = password.substring("{pbkdf2}".length());
		}
		user.setPassword(password);
		
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		List<Permission> pList = new ArrayList<>();
		pList.add(new Permission(3)); // COMMON_USER
		user.setPermissions(pList);
		
		return userMapper.toUserVO(userRepository.save(user));
	}
	
	public UserVO updateById(Integer id, UserVO vo) {
		User entity = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		entity.setUserName(vo.getUserName());
		entity.setFullName(vo.getFullName());
		entity.setAccountNonExpired(vo.getAccountNonExpired());
		entity.setAccountNonLocked(vo.getAccountNonLocked());
		entity.setCredentialsNonExpired(vo.getCredentialsNonExpired());
		entity.setEnabled(vo.getEnabled());
		entity.setPermissions(vo.getPermissions());
		
		return userMapper.toUserVO(userRepository.save(entity));
	}
	
	private static String createHash(String password) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		return passwordEncoder.encode(password);
	}
}
