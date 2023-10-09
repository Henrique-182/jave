package br.com.ibpt.repositories.v1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.Version;

@Repository
public interface VersionRepository extends JpaRepository<Version, Integer> {

	@Query("SELECT VERS.id FROM Version VERS")
	public List<Integer> findAllId();
}
