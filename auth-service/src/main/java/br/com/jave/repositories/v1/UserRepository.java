package br.com.jave.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.jave.model.v1.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(@Param("username") String username);
	
}
