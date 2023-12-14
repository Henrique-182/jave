package br.com.conhecimento.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.conhecimento.model.v1.Software;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Integer> {

}
