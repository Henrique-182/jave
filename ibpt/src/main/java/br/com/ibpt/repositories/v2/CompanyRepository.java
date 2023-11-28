package br.com.ibpt.repositories.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v2.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Modifying
	@Query("UPDATE Company COMP "
		 + "   SET COMP.isActive = :value "
		 + " WHERE COMP.id = :id "
		  )
	void updateCompanyIsActiveById(@Param("id") Integer id, @Param("value") Boolean value);
	
	@Modifying
	@Query("UPDATE CompanySoftware COSO "
		 + "   SET COSO.isActive = :value "
		 + " WHERE COSO.fkCompany = :id " 
		  )
	void updateCompanySoftwareIsActiveByFkCompany(@Param("id") Integer id, @Param("value") Boolean value);
	
	@Modifying
	@Query("UPDATE CompanySoftware COSO "
		 + "   SET COSO.isActive = :value "
		 + " WHERE COSO.id = :id " 
		  )
	void updateCompanySoftwareIsActiveById(@Param("id") Integer id, @Param("value") Boolean value);
}
