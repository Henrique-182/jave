package br.com.ibpt.unittests.mocks.v2;

import java.util.ArrayList;
import java.util.List;

import br.com.ibpt.data.vo.v3.IbptVO;
import br.com.ibpt.model.v2.Ibpt;
import br.com.ibpt.unittests.mocks.v1.VersionMock;

public class IbptMock {
	
	VersionMock versionMock = new VersionMock();
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
		entity.setVersion(versionMock.mockEntity(number));
		entity.setCompanySoftware(companySoftwareIbptMock.mockEntity(number));
		entity.setIsUpdated(
			number % 2 == 0
			? true
			: false
		);
		
		return entity;
	}
	
	public IbptVO mockVO(Integer number) {
		IbptVO vo = new IbptVO();
		vo.setKey(number);
		vo.setVersion(versionMock.mockEntity(number));
		vo.setCompanySoftware(companySoftwareIbptMock.mockEntity(number));
		vo.setIsUpdated(
			number % 2 == 0
			? true
			: false
		);
		
		return vo;
	}
}
