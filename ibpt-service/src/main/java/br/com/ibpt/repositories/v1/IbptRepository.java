package br.com.ibpt.repositories.v1;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.Ibpt;

@Repository
public interface IbptRepository extends JpaRepository<Ibpt, Integer> {
	
	@Query("SELECT IBPT.FUNC_NEW_IBPT_VOID_PLPGSQL(:p_id_version)")
	void selectFuncNewIbpt(Integer p_id_version);
	
	@Modifying
	@Query("UPDATE Ibpt IBPT "
		 + "   SET IBPT.isUpdated = :value, "
		 + "       IBPT.lastUpdateDatetime = :datetime "
		 + " WHERE (IBPT.companySoftware.id = :idCompanySoftware OR IBPT.companySoftware.fkCompanySoftwareSameDb = :idCompanySoftware) "
		 + "   AND IBPT.version.id = :idVersion "
		  )
	void updateByVersionAndCompanySoftware(
			@Param("idVersion") Integer idVersion, 
			@Param("idCompanySoftware") Integer idCompanySoftware, 
			@Param("value") Boolean value,
			@Param("datetime") Date datetime
		);
	
	List<Ibpt> findByVersionName(@Param("versionName") String versionName);
	
	@Modifying
	@Query("DELETE "
		 + "  FROM Ibpt IBPT "
		 + " WHERE IBPT.version.id = :id"
		 )
	void deleteByVersionId(@Param("id") Integer id);
	
}
