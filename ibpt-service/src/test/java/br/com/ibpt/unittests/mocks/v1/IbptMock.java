package br.com.ibpt.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.ibpt.data.vo.v1.IbptVO;
import br.com.ibpt.model.v1.Ibpt;

public class IbptMock {
	
	CompanySoftwareIbptMock companySoftwareIbptMock = new CompanySoftwareIbptMock();
	
	public Ibpt mockEntity() {
		return mockEntity(0);
	}

	public IbptVO mockVO() {
		return mockVO(0);
	}

	public List<IbptVO> mockVOList() {
		List<IbptVO> objectList = new ArrayList<>();
		
		for (int i = 0; i < 14; i++) objectList.add(mockVO(i));
		
		return objectList;
	}
	
	public Ibpt mockEntity(Integer number) {
		Ibpt entity = new Ibpt();
		entity.setId(number);
		entity.setVersion(VersionIbptMock.entity(number));
		entity.setCompanySoftware(companySoftwareIbptMock.mockEntity(number));
		entity.setIsUpdated(
			number % 2 == 0
			? true
			: false
		);
		entity.setLastUpdateDatetime(new Date());
		entity.setCreationDatetime(new Date());
		
		return entity;
	}
	
	public IbptVO mockVO(Integer number) {
		IbptVO vo = new IbptVO();
		vo.setKey(number);
		vo.setVersion(VersionIbptMock.entity(number));
		vo.setCompanySoftware(companySoftwareIbptMock.mockEntity(number));
		vo.setIsUpdated(
			number % 2 == 0
			? true
			: false
		);
		vo.setLastUpdateDatetime(new Date());
		vo.setCreationDatetime(new Date());
		
		return vo;
	}
}
