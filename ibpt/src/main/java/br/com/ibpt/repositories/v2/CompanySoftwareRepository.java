package br.com.ibpt.repositories.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v2.CompanySoftware;

@Repository
public interface CompanySoftwareRepository extends JpaRepository<CompanySoftware, Integer> {

	@Modifying
	@Query("UPDATE CompanySoftware COSO "
		 + "   SET COSO.isActive = :value "
		 + " WHERE COSO.id = :id " 
		  )
	void updateCompanySoftwareIsActiveById(@Param("id") Integer id, @Param("value") Boolean value);
}
