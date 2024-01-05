package br.com.ibpt.unittests.mocks.v3;

import br.com.ibpt.model.v3.CompanySoftwareIbpt;

public class CompanySoftwareIbptMock {

	public CompanySoftwareIbpt mockEntity(Integer number) {

		CompanySoftwareIbpt entity = new CompanySoftwareIbpt();
		entity.setId(number);
		entity.setSoftware(SoftwareIbptMock.entity(number));
		entity.setCompany(CompanyIbptMock.entity(number));
		entity.setHaveAuthorization(
				number % 2 == 0
				? true
				: false
		);
		entity.setConnection("Connection" + number);
		entity.setObservation("Observation" + number);
		entity.setFkCompanySoftwareSameDb(null);
		
		return entity;
	}
	
}
