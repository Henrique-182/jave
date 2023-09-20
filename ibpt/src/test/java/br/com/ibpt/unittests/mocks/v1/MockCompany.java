package br.com.ibpt.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.data.vo.v1.CompanyVO;
import br.com.ibpt.model.v1.Company;

public class MockCompany {

	public Company mockEntity() {
		return mockEntity(0);
	}
	
	public CompanyVO mockVO() {
		return mockVO(0);
	}
	
	public List<Company> mockEntityList() {
		List<Company> entityList = new ArrayList<>();
		for (int i = 0; i < 14; i++) entityList.add(mockEntity(i));
		return entityList;
	}
	
	public List<CompanyVO> mockVOList() {
		List<CompanyVO> voList = new ArrayList<>();
		for (int i = 0; i < 14; i++) voList.add(mockVO(i));
		return voList;
	}
	
	public Company mockEntity(Integer number) {
		
		Company entity = new Company();
		entity.setId(number);
		entity.setCnpj("" + number + number + number + number + number + number + number + number + number + number + number + number + number + number);
		entity.setTradeName("Trade Name" + number);
		entity.setBusinessName("Business Name" + number);
		entity.setSoftware(
					number % 2 == 0
					? "Stac"
					: "Esti"
				);
		entity.setHaveAuthorization(
					number % 2 == 0
					? true
					: false
				);
		entity.setConnection("" + number + number + number + number + number + number + number + number + number);
		entity.setObservation("Observation" + number);
		entity.setIsActive(
					number % 2 == 0
					? true
					: false
				);
		entity.setFkCompanySameDb(number * number);
		
		return entity;
	}
	
	public CompanyVO mockVO(Integer number) {
		
		CompanyVO vo = new CompanyVO();
		vo.setKey(number);
		vo.setCnpj("" + number + number + number + number + number + number + number + number + number + number + number + number + number + number);
		vo.setTradeName("Trade Name" + number);
		vo.setBusinessName("Business Name" + number);
		vo.setSoftware(
					number % 2 == 0
					? "Stac"
					: "Esti"
				);
		vo.setHaveAuthorization(
					number % 2 == 0
					? true
					: false
				);
		vo.setConnection("" + number + number + number + number + number + number + number + number + number);
		vo.setObservation("Observation" + number);
		vo.setIsActive(
					number % 2 == 0
					? true
					: false
				);
		vo.setFkCompanySameDb(number * number);
		
		return vo;
	}
}
