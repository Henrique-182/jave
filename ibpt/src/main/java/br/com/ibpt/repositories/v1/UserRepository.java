package br.com.ibpt.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("SELECT USER "
		 + "  FROM Users USER "
		 + " WHERE USER.userName = :userName "
		  )
	User findByUserName(@Param("userName") String userName);
}
