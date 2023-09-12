package br.com.ibpt.repositories.v1;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.ibpt.model.v1.Company;
import jakarta.persistence.EntityManager;

@Repository
public class CompanyCustomRepository {

	private EntityManager em;
	
	CompanyCustomRepository(EntityManager em) {
		this.em = em;
	}
	
	public List<Company> findCustom(
		String cnpj,
		String tradeName,
		String businessName,
		String software,
		String connection,
		Boolean haveAuthorization,
		Boolean isActive,
		Integer fkCompanySameDb
	) {
		String query = "SELECT COMP FROM Company AS COMP ";
		String condition = "WHERE ";
		
		if (cnpj != null) {
			query += condition + "COMP.cnpj LIKE :cnpj ";
			condition = "AND ";
		}
		
		if (tradeName != null) {
			query += condition + "COMP.tradeName LIKE :tradeName ";
			condition = "AND ";
		}
		
		if (businessName != null) {
			query += condition + "COMP.businessName LIKE :businessName ";
			condition = "AND ";
		}
		
		if (software != null) {
			query += condition + "COMP.software = :software ";
			condition = "AND ";
		}
		
		if (connection != null) {
			query += condition + "COMP.connection LIKE :connection ";
			condition = "AND ";
		}
		
		if (haveAuthorization != null) {
			query += condition + "COMP.haveAuthorization LIKE :haveAuthorization ";
			condition = "AND ";
		}
		
		if (isActive != null) {
			query += condition + "COMP.isActive = :isActive ";
			condition = "AND ";
		}
		
		if (fkCompanySameDb != null) {
			if (fkCompanySameDb == 0) query += condition + "COMP.fkCompanySameDb IS NULL ";
			else query += condition + "COMP.fkCompanySameDb = :fkCompanySameDb ";
		} 
		
		var q = em.createQuery(query, Company.class);
		
		if(cnpj != null) {
			q.setParameter("cnpj", "%" + cnpj + "%");
		}
		
		if (tradeName != null) {
			q.setParameter("tradeName", "%" + tradeName + "%");
		}
		
		if (businessName != null) {
			q.setParameter("businessName", "%" + businessName + "%");
		}
		
		if (software != null) {
			q.setParameter("software", software);
		}
		
		if (connection != null) {
			q.setParameter("connection", "%" + connection + "%");
		}
		
		if (haveAuthorization != null) {
			q.setParameter("haveAuthorization", haveAuthorization);
		}
		
		if (isActive != null) {
			q.setParameter("isActive", isActive);
		}
		
		if (fkCompanySameDb != null) {
			if (fkCompanySameDb != 0) q.setParameter("fkCompanySameDb", fkCompanySameDb);
		}
		
		return q.getResultList();
	}
}
