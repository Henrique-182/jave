package br.com.ibpt.unittests.mocks.v2;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.model.v2.CompanySoftware;

public class CompanySoftwareMock {

	SoftwareMock mock = new SoftwareMock();

	public List<CompanySoftware> mockEntity(Integer number) {

		List<CompanySoftware> entityList = new ArrayList<>();
		
		entityList.add(mockByType(number, "Fiscal"));
		
		if (number % 2 == 0) entityList.add(mockByType(number, "Geral"));
		
		return entityList;
	}
	
	private CompanySoftware mockByType(Integer number, String type) {
		CompanySoftware entity = new CompanySoftware();
		entity.setId(
				type.equalsIgnoreCase("Fiscal")
				? number
				: number + number
		);
		entity.setSoftware(mock.mockEntity(number));
		entity.setType(type);
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
