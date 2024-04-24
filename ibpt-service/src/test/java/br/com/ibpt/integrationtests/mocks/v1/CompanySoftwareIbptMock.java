package br.com.ibpt.integrationtests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.model.v1.CompanySoftwareIbpt;
import br.com.ibpt.model.v1.SoftwareType;

public class CompanySoftwareIbptMock {
	
	SoftwareIbptMock mock = new SoftwareIbptMock();

	public List<CompanySoftwareIbpt> mockEntity(Integer number) {

		List<CompanySoftwareIbpt> entityList = new ArrayList<>();
		
		CompanySoftwareIbpt entity = new CompanySoftwareIbpt();
		entity.setSoftware(mock.mockEntity(number));
		entity.setType(SoftwareType.Fiscal);
		entity.setHaveAuthorization(
				number % 2 == 0
				? true
				: false
		);
		entity.setConnection("Connection" + number);
		entity.setObservation("Observation" + number);
		entity.setIsActive(true);
		entity.setFkCompanySoftwareSameDb(null);
		
		entityList.add(entity);
		
		return entityList;
	}
	
}
