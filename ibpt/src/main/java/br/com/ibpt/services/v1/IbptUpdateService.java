package br.com.ibpt.services.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.IbptUpdateVO;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.model.v1.IbptUpdate;
import br.com.ibpt.repositories.v1.IbptUpdateCustomRepository;
import br.com.ibpt.repositories.v1.IbptUpdateRepository;

@Service
public class IbptUpdateService {

	@Autowired
	private IbptUpdateRepository ibptUpdateRepository;
	
	@Autowired
	private IbptUpdateCustomRepository ibptUpdateCustomRepository;
	
	public List<IbptUpdateVO> findCustom(
		String versionName, 
		String companyCnpj, 
		String companyBusinessName, 
		String companyTradeName
	) {
		var resultList = ibptUpdateCustomRepository.findCustom(versionName, companyCnpj, companyBusinessName, companyTradeName);
		
		List<IbptUpdateVO> voList = new ArrayList<>();
		
		for (Object[] result : resultList) {
			IbptUpdateVO vo = new IbptUpdateVO();
			
			vo.setId((Integer) result[0]);
			vo.setVersionName((String) result[1]);
			vo.setCompanyCnpj((String) result[2]);
			vo.setCompanyTradeName((String) result[3]);
			vo.setCompanyBusinessName((String) result[4]);
			vo.setIsUpdated((Boolean) result[5]);
			
			voList.add(vo);
		} 
		
		return voList;
	}
	
	public void updateById(Integer id) {
		List<IbptUpdate> entityList = new ArrayList<>();
		List<Integer> idList = new ArrayList<>();
		
		IbptUpdate entity = ibptUpdateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		entityList.add(entity);
		
		entityList.addAll(ibptUpdateRepository.findByCompanyAndFkVersion(entity.getFkCompany(), entity.getFkVersion()));
		 
		for (IbptUpdate e : entityList) {
			System.out.println(e.getId());
			idList.add(e.getId());
		}
		
		ibptUpdateRepository.updateAll(idList);
	}
	
	public void callProcNewIbptUpdate() {
		ibptUpdateRepository.PROC_NEW_IBPT_UPDATE();
	}
}
