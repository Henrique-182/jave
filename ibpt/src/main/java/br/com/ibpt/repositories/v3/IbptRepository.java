package br.com.ibpt.repositories.v3;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v3.Ibpt;
import br.com.ibpt.model.v3.UserAudit;

@Repository
public interface IbptRepository extends JpaRepository<Ibpt, Integer> {
	
	@Procedure("PROC_NEW_IBPT")
	void callProcNewIbpt(Integer p_id_version);

	@Modifying
	@Query("UPDATE Ibpt IBPT "
		 + "   SET IBPT.isUpdated = :value, "
		 + "       IBPT.userLastUpdate = :userAudit, "
		 + "       IBPT.lastUpdateDatetime = :datetime "
		 + " WHERE (IBPT.companySoftware.id = :idCompanySoftware OR IBPT.companySoftware.fkCompanySoftwareSameDb = :idCompanySoftware) "
		 + "   AND IBPT.version.id = :idVersion "
		  )
	void updateByVersionAndCompanySoftware(
			@Param("idVersion") Integer idVersion, 
			@Param("idCompanySoftware") Integer idCompanySoftware, 
			@Param("value") Boolean value,
			@Param("userAudit") UserAudit userAudit,
			@Param("datetime") Date datetime
		);
	
}
