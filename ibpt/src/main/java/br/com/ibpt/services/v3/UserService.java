package br.com.ibpt.services.v3;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

import br.com.ibpt.controllers.v3.UserController;
import br.com.ibpt.data.vo.v3.AccountCredentialsVO;
import br.com.ibpt.data.vo.v3.UserVO;
import br.com.ibpt.exceptions.v1.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.v1.ResourceNotFoundException;
import br.com.ibpt.mappers.v3.UserMapper;
import br.com.ibpt.model.v1.Permission;
import br.com.ibpt.model.v3.User;
import br.com.ibpt.repositories.v3.UserRepository;

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
		var user = repository.findByUsername(username);
		
		if(user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username (" + username + ") not found!");
		}
	}
	
	public PagedModel<EntityModel<UserVO>> findAllPageable(Pageable pageable) {
		
		Page<User> entityList = repository.findAll(pageable);
		
		Page<UserVO> voList = entityList.map(u -> mapper.toVO(u));
		
		voList.map(u -> addLinkSelfRel(u));
		
		return assembler.toModel(voList);
	}
	
	public UserVO findById(Integer id) {
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(entity));
	}
	
	public UserVO create(AccountCredentialsVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		User user = new User();
		user.setUsername(data.getUsername());
		user.setFullname(data.getFullname());
		
		String password = createHash(data.getPassword());
		if (password.startsWith("{pbkdf2}")) password = password.substring("{pbkdf2}".length());
		user.setPassword(password);
		
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		List<Permission> pList = new ArrayList<>();
		pList.add(new Permission(3)); // COMMON_USER
		user.setPermissions(pList);
		
		User persistedUser = repository.save(user);
		
		return addLinkVOList(mapper.toVO(persistedUser));
	}
	
	public UserVO updateById(Integer id, UserVO vo) {
		if (vo == null) throw new RequiredObjectIsNullException();
		
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setUsername(vo.getUsername());
		entity.setFullname(vo.getFullname());
		entity.setAccountNonExpired(vo.getAccountNonExpired());
		entity.setAccountNonLocked(vo.getAccountNonLocked());
		entity.setCredentialsNonExpired(vo.getCredentialsNonExpired());
		entity.setEnabled(vo.getEnabled());
		entity.setPermissions(vo.getPermissions());
		
		User updatedUser = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedUser));
	}
	
	public void deleteById(Integer id) {
		User entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private UserVO addLinkSelfRel(UserVO vo) {
		return vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private UserVO addLinkVOList(UserVO vo) {
		return vo.add(linkTo(methodOn(UserController.class).findAllPageable(0, 10, "asc", "username")).withRel("userVOList").expand());
	}
	
	private static String createHash(String password) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		return passwordEncoder.encode(password);
	}
}
