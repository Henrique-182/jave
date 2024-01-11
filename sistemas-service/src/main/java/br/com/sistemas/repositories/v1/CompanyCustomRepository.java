package br.com.sistemas.repositories.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import br.com.sistemas.model.v1.Company;
import jakarta.persistence.EntityManager;

@Repository
public class CompanyCustomRepository {

	EntityManager entityManager;
	
	@Autowired
	public CompanyCustomRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Map<String, Object> findCustom(Pageable pageable, Map<String, Object> params) {
		String cnpj = (String) params.get("cnpj");
		String name = (String) params.get("name");
		Boolean isActive = (Boolean) params.get("isActive");
		String softwareName = (String) params.get("softwareName");
		String softwareType = (String) params.get("softwareType");
		Integer softwareFkSameDb = (Integer) params.get("softwareFkSameDb");
		
		String query = "SELECT COMP FROM Company COMP ";
		String condicao = "WHERE ";
		
		if (cnpj != null) {
			query += condicao + "COMP.cnpj LIKE :cnpj ";
			condicao = "AND ";
		}
		
		if (name != null) {
			query += condicao + "(COMP.tradeName ILIKE :name) OR (COMP.businessName ILIKE :name) ";
			condicao = "AND ";
		}
		
		if (isActive != null) {
			query += condicao + "COMP.isActive = :isActive ";
			condicao = "AND ";
		}
		
		if (softwareName != null) {
			query += condicao + "ELEMENT(COMP.softwares).software.name = :softwareName ";
			condicao = "AND ";
		}
		
		if (softwareType != null) {
			query += condicao + "CAST(ELEMENT(COMP.softwares).type AS string) = :softwareType ";
			condicao = "AND ";
		}
		
		if (softwareFkSameDb != null) {
			query += condicao + "ELEMENT(COMP.softwares).fkCompanySoftwareSameDb = :softwareFkSameDb ";
			condicao = "AND ";
		}
		
		String queryCount = query;

		Order order = pageable.getSort().get().toList().get(0);
			
		query += "ORDER BY " + "COMP." + order.getProperty() + " ";
		query += " " + order.getDirection() + " ";
		query += " LIMIT " + pageable.getPageSize() + " ";
		query += " OFFSET " + pageable.getOffset() + " ";
		
		var q = entityManager.createQuery(query, Company.class);
		
		if (cnpj != null) {
			q.setParameter("cnpj", "%" + cnpj + "%");
		}
		
		if (name != null) {
			q.setParameter("name", "%" + name + "%");
		}
		
		if (isActive != null) {
			q.setParameter("isActive", isActive);
		}
		
		if (softwareName != null) {
			q.setParameter("softwareName", softwareName);
		}
		
		if (softwareType != null) {
			q.setParameter("softwareType", softwareType);
		}
		
		if (softwareFkSameDb != null) {
			q.setParameter("softwareFkSameDb", softwareFkSameDb);
		}
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", q.getResultList());
		resultMap.put("totalElements", countByWhere(queryCount, cnpj, name, isActive, softwareName, softwareType, softwareFkSameDb));
		
		return resultMap;
	}
	
	private long countByWhere(
		String queryCount,
		String cnpj,
		String name,
		Boolean isActive,
		String softwareName,
		String softwareType,
		Integer softwareFkSameDb
	) {
		queryCount = queryCount.replace("SELECT COMP", "SELECT COUNT(COMP)");
		
		var q = entityManager.createQuery(queryCount, Long.class);
		
		if (cnpj != null) {
			q.setParameter("cnpj", "%" + cnpj + "%");
		}
		
		if (name != null) {
			q.setParameter("name", "%" + name + "%");
		}
		
		if (isActive != null) {
			q.setParameter("isActive", isActive);
		}
		
		if (softwareName != null) {
			q.setParameter("softwareName", softwareName);
		}
		
		if (softwareType != null) {
			q.setParameter("softwareType", softwareType);
		}
		
		if (softwareFkSameDb != null) {
			q.setParameter("softwareFkSameDb", softwareFkSameDb);
		}
		
		return (long) q.getSingleResult();
	}
	
}
