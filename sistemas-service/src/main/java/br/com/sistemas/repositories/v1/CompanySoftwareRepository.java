package br.com.sistemas.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sistemas.model.v1.CompanySoftware;

@Repository
public interface CompanySoftwareRepository extends JpaRepository<CompanySoftware, Integer>{

}
