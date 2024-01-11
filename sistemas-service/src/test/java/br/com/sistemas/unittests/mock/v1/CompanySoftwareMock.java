package br.com.sistemas.unittests.mock.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.sistemas.model.v1.CompanySoftware;
import br.com.sistemas.model.v1.SoftwareType;

public class CompanySoftwareMock {

	public static List<CompanySoftware> entity(Integer number) {

		List<CompanySoftware> entityList = new ArrayList<>();
		
		entityList.add(mockByType(number, SoftwareType.Fiscal));
		
		if (number % 2 == 0) entityList.add(mockByType(number, SoftwareType.Geral));
		
		return entityList;
	}
	
	private static CompanySoftware mockByType(Integer number, SoftwareType type) {
		CompanySoftware entity = new CompanySoftware();
		entity.setKey(
				type.toString().equalsIgnoreCase(SoftwareType.Fiscal.toString())
				? number
				: number + number
		);
		entity.setSoftware(SoftwareCompMock.entity(number));
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
