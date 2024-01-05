package br.com.ibpt.repositories.v3;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v2.Company;
import jakarta.persistence.EntityManager;

@Repository
public class CompanyCustomRepository {

	EntityManager entityManager;
	
	@Autowired
	public CompanyCustomRepository(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public List<Company> findCustom(
		Pageable pageable,
		String cnpj,
		String name,
		Boolean isActive,
		String softwareName,
		String softwareType,
		Integer softwareFkSameDb
	) {
		String query = "SELECT COMP FROM Company COMP ";
		String condicao = "WHERE ";
		
		if (cnpj != null) {
			query += condicao + "COMP.cnpj LIKE :cnpj ";
			condicao = "AND ";
		}
		
		if (name != null) {
			query += condicao + "(COMP.tradeName LIKE :name) OR (COMP.businessName LIKE :name) ";
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
			query += condicao + "ELEMENT(COMP.softwares).type = :softwareType ";
			condicao = "AND ";
		}
		
		if (softwareFkSameDb != null) {
			query += condicao + "ELEMENT(COMP.softwares).fkCompanySoftwareSameDb = :softwareFkSameDb ";
			condicao = "AND ";
		}
		
		if (pageable.getSort() != null) {
			
			Order order = pageable.getSort().get().toList().get(0);
			
			query += "ORDER BY " + "COMP." + order.getProperty() + " ";
			
			if (order.getDirection() != null) {
				query += order.getDirection() + " ";
			}
		}
		
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
		
		return q.getResultList();
	}

}
