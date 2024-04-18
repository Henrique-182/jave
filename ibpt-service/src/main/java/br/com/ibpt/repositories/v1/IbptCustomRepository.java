package br.com.ibpt.repositories.v1;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.Ibpt;
import jakarta.persistence.EntityManager;

@Repository
public class IbptCustomRepository {
	
	private EntityManager entityManager;

	public IbptCustomRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Ibpt> findCustom(
		Pageable pageable,
		String versionName,
		String companyCnpj,
		String companyName,
		Boolean isUpdated
	) {
		String query = "SELECT IBPT FROM Ibpt IBPT ";
		String condition = "WHERE ";
		
		if (versionName != null) {
			query += condition + " IBPT.version.name ILIKE :versionName ";
			condition = "AND ";
		}
		
		if (companyCnpj != null) {
			query += condition + " IBPT.companySoftware.company.cnpj ILIKE :companyCnpj ";
			condition = "AND ";
		}
		
		if (companyName != null) {
			query += condition + " (IBPT.companySoftware.company.tradeName ILIKE :companyName OR IBPT.companySoftware.company.businessName LIKE :companyName) ";
			condition = "AND ";
		}
		
		if (isUpdated != null) {
			query += condition + " IBPT.isUpdated = :isUpdated ";
			condition = "AND ";
		}
		
		if (pageable.getSort() != null) {
			
			Order order = pageable.getSort().get().toList().get(0);
			
			query += " ORDER BY IBPT." + order.getProperty() + " ";
			
			if (order.getDirection() != null) {
				query += order.getDirection();
			}
		}
		
		query += " LIMIT " + pageable.getPageSize() + " ";
		query += " OFFSET " + pageable.getOffset();
		
		var q = entityManager.createQuery(query, Ibpt.class);
		
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
