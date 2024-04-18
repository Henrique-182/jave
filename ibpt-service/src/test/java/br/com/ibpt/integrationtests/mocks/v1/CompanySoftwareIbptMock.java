package br.com.ibpt.integrationtests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.model.v1.CompanySoftwareIbpt;

public class CompanySoftwareIbptMock {
	
	SoftwareIbptMock mock = new SoftwareIbptMock();

	public List<CompanySoftwareIbpt> mockEntity(Integer number) {

		List<CompanySoftwareIbpt> entityList = new ArrayList<>();
		
		entityList.add(mockByType(number, "Fiscal"));
		
		if (number % 2 == 0) entityList.add(mockByType(number, "Geral"));
		
		return entityList;
	}
	
	private CompanySoftwareIbpt mockByType(Integer number, String type) {
		CompanySoftwareIbpt entity = new CompanySoftwareIbpt();
		entity.setSoftware(mock.mockEntity(number));
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
