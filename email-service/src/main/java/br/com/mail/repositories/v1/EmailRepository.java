package br.com.mail.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mail.model.v1.Email;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

}
