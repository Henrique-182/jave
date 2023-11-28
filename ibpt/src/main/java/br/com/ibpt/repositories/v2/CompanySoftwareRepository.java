package br.com.ibpt.repositories.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v2.CompanySoftware;

@Repository
public interface CompanySoftwareRepository extends JpaRepository<CompanySoftware, Integer> {

}
