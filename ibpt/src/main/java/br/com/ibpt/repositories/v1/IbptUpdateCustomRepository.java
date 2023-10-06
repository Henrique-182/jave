package br.com.ibpt.repositories.v1;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;

@Repository
public class IbptUpdateCustomRepository {

	private EntityManager em;
	
	public IbptUpdateCustomRepository(EntityManager em) {
		this.em = em;
	}
	
	public List<Object[]> findCustom(
		String versionName,
		String companyCnpj,
		String companyBusinessName, 
		String companyTradeName,
		Boolean isUpdated
	) {
		String query = "SELECT IBUP.id "
				+ "          , VERS.name "
				 + "         , COMP.cnpj "
				 + "         , COMP.tradeName "
				 + "         , COMP.businessName "
				 + "         , IBUP.isUpdated "
				 + "      FROM IbptUpdate IBUP "
				 + "INNER JOIN Company COMP ON IBUP.fkCompany = COMP.id "
				 + "INNER JOIN Version VERS ON IBUP.fkVersion = VERS.id ";
		String condition = "WHERE ";
		
		if (versionName != null) {
			query += condition + "VERS.name = :versionName ";
			condition = "AND ";
		}
		
		if (companyCnpj != null) {
			query += condition + "COMP.cnpj LIKE :companyCnpj ";
			condition = "AND ";
		}
		
		if (companyBusinessName != null) {
			query += condition + "COMP.businessName LIKE :companyBusinessName ";
			condition = "AND ";
		}
		
		if (companyTradeName != null) {
			query += condition + "COMP.tradeName LIKE :companyTradeName ";
			condition = "AND ";
		}
		
		if (isUpdated != null) {
			query += condition + "IBUP.isUpdated = :isUpdated ";
			condition = "AND";
		}
				 
		var q = em.createQuery(query, Object[].class);
		
		if (versionName != null) {
			q.setParameter("versionName", versionName);
		}
		
		if (companyCnpj != null) {
			q.setParameter("companyCnpj", "%" + companyCnpj + "%");
		}
		
		if (companyBusinessName != null) {
			q.setParameter("companyBusinessName", "%" + companyBusinessName + "%");
		}
		
		if (companyTradeName != null) {
			q.setParameter("companyTradeName", "%" + companyTradeName + "%");
		}
		
		if (isUpdated != null) {
			q.setParameter("isUpdated", isUpdated);
		}
		
		return q.getResultList();
	}
}
