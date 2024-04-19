package br.com.jave.repositories.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jave.model.v1.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

	User findByUsername(@Param("username") String username);
	
	Page<User> findPageableByUsernameContainingAndPermissionsDescriptionContaining(
		@Param("username") String username,
		@Param("permission") String permission,
		Pageable pageable
	);
	
}
