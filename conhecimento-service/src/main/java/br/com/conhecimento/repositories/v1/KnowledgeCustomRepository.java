package br.com.conhecimento.repositories.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import br.com.conhecimento.model.v1.Knowledge;
import jakarta.persistence.EntityManager;

@Repository
public class KnowledgeCustomRepository {

	private EntityManager entityManager;

	public KnowledgeCustomRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Map<String, Object> findCustomPageable(Pageable pageable, Map<String, Object> params) {
		String knowledgeTitle = (String) params.get("knowledgeTitle");
		String knowledgeDescription = (String) params.get("knowledgeDescription");
		String knowledgeContent = (String) params.get("knowledgeContent");
		
		String query = "SELECT KNOW FROM Knowledge KNOW ";
		String condition = "WHERE ";
		
		if (knowledgeTitle != null) {
			query += condition + "KNOW.title ILIKE :knowledgeTitle ";
			condition = "AND ";
		}
		
		if (knowledgeDescription != null) {
			query += condition + "KNOW.description ILIKE :knowledgeDescription ";
			condition = "AND ";
		}
		
		if (knowledgeContent != null) {
			query += condition + "KNOW.content ILIKE :knowledgeContent ";
			condition = "AND ";
		}
		
		String queryCount = query;
		
		Order order = pageable.getSort().get().toList().get(0);
		
		query += "ORDER BY KNOW." + order.getProperty() + " " + order.getDirection() + " ";
		query += "LIMIT " + pageable.getPageSize() + " ";
		query += "OFFSET " + pageable.getOffset();
		
		var q = entityManager.createQuery(query, Knowledge.class);
		
		if (knowledgeTitle != null) {
			q.setParameter("knowledgeTitle", "%" + knowledgeTitle + "%");
		}
		
		if (knowledgeDescription != null) {
			q.setParameter("knowledgeDescription", "%" + knowledgeDescription + "%");
		}
		
		if (knowledgeContent != null) {
			q.setParameter("knowledgeContent", "%" + knowledgeContent + "%");
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", q.getResultList());
		resultMap.put("totalElements", countByWhere(queryCount, params));
		
		return resultMap;
	}
	
	private long countByWhere(String queryCount, Map<String, Object> params) {
		String knowledgeTitle = (String) params.get("knowledgeTitle");
		String knowledgeDescription = (String) params.get("knowledgeDescription");
		String knowledgeContent = (String) params.get("knowledgeContent");
		
		queryCount = queryCount.replace("SELECT KNOW", "SELECT COUNT(KNOW)");
		
		var q = entityManager.createQuery(queryCount, Long.class);
		
		if (knowledgeTitle != null) {
			q.setParameter("knowledgeTitle", knowledgeTitle);
		}
		
		if (knowledgeDescription != null) {
			q.setParameter("knowledgeDescription", knowledgeDescription);
		}
		
		if (knowledgeContent != null) {
			q.setParameter("knowledgeContent", knowledgeContent);
		}
		
		return q.getSingleResult();
	}
	
}
