package br.com.conhecimento.repositories.v2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.conhecimento.model.v2.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

	User findByUsername(@Param("username") String username);
	
	Page<User> findPageableByUsernameContainingAndPermissionsDescriptionContaining(
		@Param("username") String username,
		@Param("permissionsDescription") String permissionsDescription,
		Pageable pageable
	);
	
}
