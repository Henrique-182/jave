package br.com.ibpt.repositories.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.Version;
import jakarta.persistence.EntityManager;

@Repository
public class VersionCustomRepository {

	@Autowired
	private EntityManager em;
	
	public VersionCustomRepository(EntityManager em) {
		this.em = em;
	}
	
	public List<Version> findCustom(String name, String effectivePeriodMonth, String effectivePeriodYear) {
		String query = "SELECT VERS FROM Version AS VERS ";
		String condicao = "WHERE ";
		
		if (name != null) {
			query += condicao + "VERS.name LIKE :name ";
			condicao = "AND ";
		}
		
		if (effectivePeriodMonth != null) {
			query += condicao + "MONTH(VERS.effectivePeriodUntil) = :effectivePeriodMonth ";
			condicao = "AND ";
		}
		
		if (effectivePeriodYear != null) {
			query += condicao + "YEAR(VERS.effectivePeriodUntil) = :effectivePeriodYear ";
			condicao = "AND ";
		}
		
		var q = em.createQuery(query, Version.class);
		
		if (name != null) {
			q.setParameter("name", "%" + name + "%");
		}
		
		if (effectivePeriodMonth != null) {
			q.setParameter("effectivePeriodMonth", effectivePeriodMonth);
		}
		
		if (effectivePeriodYear != null) {
			q.setParameter("effectivePeriodYear", effectivePeriodYear);
		}
		
		return q.getResultList();
	}
}
