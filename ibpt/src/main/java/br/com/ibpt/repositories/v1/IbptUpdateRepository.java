package br.com.ibpt.repositories.v1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibpt.model.v1.IbptUpdate;

@Repository
public interface IbptUpdateRepository extends JpaRepository<IbptUpdate, Integer> {

	@Procedure
	void PROC_NEW_IBPT_UPDATE(Integer P_ID_VERSION);
	
	@Query(
		   "    SELECT IBUP "
		 + "      FROM IbptUpdate IBUP "
	     + "INNER JOIN Company COMP ON IBUP.fkCompany = COMP.id "
	     + "	 WHERE COMP.fkCompanySameDb = :fkCompany "
	     + "       AND IBUP.fkVersion = :fkVersion "
	       )
	public List<IbptUpdate> findByCompanyAndFkVersion(Integer fkCompany, Integer fkVersion);
	
	@Modifying
	@Transactional
	@Query(
		   "    UPDATE IbptUpdate IBUP "
	     + "       SET IBUP.isUpdated = :value "
	     + "	 WHERE IBUP.id IN :idList "
	      )
	public void updateAll(List<Integer> idList, Boolean value);
}
