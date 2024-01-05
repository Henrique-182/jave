package br.com.ibpt.repositories.v3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v3.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT USER "
		 + "  FROM User USER "
		 + " WHERE USER.username = :username "
		  )
	User findByUsername(@Param("username") String username);
	
}
