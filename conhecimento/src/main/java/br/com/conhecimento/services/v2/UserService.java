package br.com.conhecimento.services.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.conhecimento.controllers.v2.UserController;
import br.com.conhecimento.data.vo.v2.AccountCredentialsVO;
import br.com.conhecimento.data.vo.v2.UserVO;
import br.com.conhecimento.exceptions.v1.RequiredObjectIsNullException;
import br.com.conhecimento.exceptions.v1.ResourceNotFoundException;
import br.com.conhecimento.mappers.v2.UserMapper;
import br.com.conhecimento.model.v2.Permission;
import br.com.conhecimento.model.v2.User;
import br.com.conhecimento.repositories.v2.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<UserVO> assembler;
	
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		
		if (user != null) return user;
		else throw new UsernameNotFoundException("Username (" + username + ") not found!");
	}
	
	public PagedModel<EntityModel<UserVO>> findCustomPageable(
		String username, 
		String permissionDescription, 
		Pageable pageble
	) {
		var entityList = repository.findPageableByUsernameContainingAndPermissionsDescriptionContaining(username, permissionDescription, pageble);
		
		var voList = entityList.map(u -> mapper.toVO(u));
		voList.map(u -> addLinkSelfRel(u));
		
		return assembler.toModel(voList);
	}
	
	public UserVO findById(Integer id) {
		User persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		return addLinkVOList(mapper.toVO(persistedEntity));
	}
	
	public UserVO create(AccountCredentialsVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		User user = new User();
		user.setUsername(data.getUsername());
		user.setFullname(data.getFullname());
		
		String password = createHash(data.getPassword());
		if (password.startsWith("Bearer ")) password = password.substring("Bearer ".length());
		user.setPassword(password);
		
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		List<Permission> permissions = new ArrayList<>();
		permissions.add(new Permission(3)); // COMMON_USER
		user.setPermissions(permissions);
		
		User createdUser = repository.save(user);
		
		return addLinkVOList(mapper.toVO(createdUser));
	}
	
	public UserVO updateById(Integer id, UserVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setFullname(data.getFullname());
		entity.setAccountNonExpired(data.getAccountNonExpired());
		entity.setAccountNonLocked(data.getAccountNonLocked());
		entity.setCredentialsNonExpired(data.getCredentialsNonExpired());
		entity.setEnabled(data.getEnabled());
		entity.setPermissions(data.getPermissions());
		
		User updatedEntity = repository.save(entity);
		
		return addLinkVOList(mapper.toVO(updatedEntity));
	}
	
	public void deleteById(Integer id) {
		
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}
	
	private UserVO addLinkSelfRel(UserVO vo) {
		return vo.add(linkTo(methodOn(UserController.class).findById(vo.getKey())).withSelfRel());
	}
	
	private UserVO addLinkVOList(UserVO vo) {
		return vo.add(linkTo(methodOn(UserController.class).findCustomPageable(0, 10, "username", "asc", null, null)).withRel("userVOList").expand());
	}
	
	private String createHash(String password) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		return passwordEncoder.encode(password);
	}
	
}
