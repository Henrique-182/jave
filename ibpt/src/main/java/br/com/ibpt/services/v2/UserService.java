package br.com.ibpt.services.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
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
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<UserVO> assembler;

	public UserService(UserRepository userRepository) {
		this.repository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = repository.findByUserName(username);
		
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username (" + username + ") not found!");
		}
	}
	
	public PagedModel<EntityModel<UserVO>> findCustomPageable(Pageable pageable) {
		
		Page<User> entityList = repository.findAll(pageable);
		
		Page<UserVO> voList = entityList.map(u -> mapper.toUserVO(u));
		
		return assembler.toModel(voList);
	}
	
	public UserVO findById(Integer id) {
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		
		return mapper.toUserVO(entity);
	}
	
	public UserVO create(AccountCredentialsVO data) {
		User user = new User();
		user.setUserName(data.getUserName());
		user.setFullName(data.getFullname());
		
		String password = createHash(data.getPassword());
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
		
		return mapper.toUserVO(repository.save(user));
	}
	
	public UserVO updateById(Integer id, UserVO vo) {
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		entity.setUserName(vo.getUserName());
		entity.setFullName(vo.getFullName());
		entity.setAccountNonExpired(vo.getAccountNonExpired());
		entity.setAccountNonLocked(vo.getAccountNonLocked());
		entity.setCredentialsNonExpired(vo.getCredentialsNonExpired());
		entity.setEnabled(vo.getEnabled());
		entity.setPermissions(vo.getPermissions());
		
		return mapper.toUserVO(repository.save(entity));
	}
	
	public void deleteById(Integer id) {
		User entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private static String createHash(String password) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		return passwordEncoder.encode(password);
	}
}
