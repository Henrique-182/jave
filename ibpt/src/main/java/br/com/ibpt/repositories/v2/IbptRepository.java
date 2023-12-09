package br.com.ibpt.repositories.v2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v2.Ibpt;

@Repository
public interface IbptRepository extends JpaRepository<Ibpt, Integer> {
	
	@Procedure("PROC_NEW_IBPT")
	void callProcNewIbpt(Integer p_id_version);

	@Modifying
	@Query("UPDATE Ibpt IBPT "
		 + "   SET IBPT.isUpdated = :value "
		 + " WHERE (IBPT.companySoftware.id = :idCompanySoftware OR IBPT.companySoftware.fkCompanySoftwareSameDb = :idCompanySoftware) "
		 + "   AND IBPT.version.id = :idVersion "
		  )
	void updateByVersionAndCompanySoftware(@Param("idVersion") Integer idVersion, @Param("idCompanySoftware") Integer idCompanySoftware, @Param("value") Boolean value);
	
}
