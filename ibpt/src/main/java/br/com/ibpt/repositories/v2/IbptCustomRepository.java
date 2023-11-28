package br.com.ibpt.repositories.v2;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v2.Ibpt;
import jakarta.persistence.EntityManager;

@Repository
public class IbptCustomRepository {

	private EntityManager entityManager;

	public IbptCustomRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Ibpt> findCustom(
		Integer id,
		String versionName,
		String companyCnpj,
		String companyName,
		Boolean isUpdated,
		String sortBy,
		String direction
	) {
		String query = "SELECT IBPT FROM Ibpt IBPT ";
		String condition = "WHERE ";
		
		if (id != null) {
			query += condition + " IBPT.id = :id ";
			condition = "AND ";
		}
		
		if (versionName != null) {
			query += condition + " IBPT.version.name LIKE :versionName ";
			condition = "AND ";
		}
		
		if (companyCnpj != null) {
			query += condition + " IBPT.companySoftware.company.cnpj LIKE :companyCnpj ";
			condition = "AND ";
		}
		
		if (companyName != null) {
			query += condition + " (IBPT.companySoftware.company.tradeName LIKE :companyName OR IBPT.companySoftware.company.businessName LIKE :companyName) ";
			condition = "AND ";
		}
		
		if (isUpdated != null) {
			query += condition + " IBPT.isUpdated = :isUpdated ";
			condition = "AND ";
		}
		
		if (sortBy != null) {
			query += "ORDER BY IBPT." + sortBy + " ";
			
			if (direction != null) {
				query += direction;
			}
		}
		
		var q = entityManager.createQuery(query, Ibpt.class);
		
		if (id != null) {
			q.setParameter("id", id);
		}
		
		if (versionName != null) {
			q.setParameter("versionName", "%" + versionName + "%");
		}
		
		if (companyCnpj != null) {
			q.setParameter("companyCnpj", "%" + companyCnpj + "%");
		}
		
		if (companyName != null) {
			q.setParameter("companyName", "%" + companyName + "%");
		}
		
		if (isUpdated != null) {
			q.setParameter("isUpdated", isUpdated);
		}
		
		return q.getResultList();
	}
}
