package br.com.ibpt.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {

}
