package br.com.conhecimento.repositories.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.conhecimento.model.v2.UserAudit;

@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, Integer> {

	UserAudit findByUsername(@Param("username") String username);
	
}
