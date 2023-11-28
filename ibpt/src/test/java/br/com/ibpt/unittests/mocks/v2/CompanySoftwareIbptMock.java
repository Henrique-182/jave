package br.com.ibpt.unittests.mocks.v2;

import br.com.ibpt.model.v2.CompanySoftwareIbpt;

public class CompanySoftwareIbptMock {

	SoftwareIbptMock softwareMock = new SoftwareIbptMock();
	CompanyIbptMock companyMock = new CompanyIbptMock();

	public CompanySoftwareIbpt mockEntity(Integer number) {

		CompanySoftwareIbpt entity = new CompanySoftwareIbpt();
		entity.setId(number);
		entity.setSoftware(softwareMock.mockEntity(number));
		entity.setCompany(companyMock.mockEntity(number));
		entity.setType("Fiscal");
		entity.setHaveAuthorization(
				number % 2 == 0
				? true
				: false
		);
		entity.setConnection("Connection" + number);
		entity.setObservation("Observation" + number);
		entity.setIsActive(
				number % 2 == 0
				? true
				: false
		);
		entity.setFkCompanySoftwareSameDb(null);
		
		return entity;
	}
	
}
