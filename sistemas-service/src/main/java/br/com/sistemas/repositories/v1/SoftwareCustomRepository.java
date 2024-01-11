package br.com.sistemas.repositories.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import br.com.sistemas.model.v1.Software;
import jakarta.persistence.EntityManager;

@Repository
public class SoftwareCustomRepository {

	private EntityManager entityManager;

	public SoftwareCustomRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Map<String, Object> findCustom(Pageable pageable, String softwareName) {
		
		String query = "SELECT SOFT FROM Software SOFT ";
		String condition = "WHERE ";
		
		if (softwareName != null) {
			query += condition + "SOFT.name ILIKE :softwareName ";
			condition = "AND ";
		}
		
		String queryWithWhere = query;
		
		if (pageable.getSort() != null) {
			
			Order order = pageable.getSort().get().toList().get(0);
			
			query += "ORDER BY SOFT." + order.getProperty() + " " + order.getDirection() + " "; 
		}
			
		query += "LIMIT " + pageable.getPageSize();
		query += "OFFSET " + pageable.getOffset();
		
		var q = entityManager.createQuery(query, Software.class);
		
		if (softwareName != null) {
			q.setParameter("softwareName", "%" + softwareName + "%");
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", q.getResultList());
		resultMap.put("totalElements", countByWhere(queryWithWhere, softwareName));
		
		return resultMap;
	}
	
	public long countByWhere(String query, String softwareName) {
		
		query = query.replace("SELECT SOFT", "SELECT COUNT(SOFT)");
		
		var q = entityManager.createQuery(query);
		
		if (softwareName != null) {
			q.setParameter("softwareName", "%" + softwareName + "%");
		}
		
		return (long) q.getSingleResult();
	}
	
}
