package br.com.ibpt.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.VersionVO;
import br.com.ibpt.exceptions.RequiredObjectIsNullException;
import br.com.ibpt.exceptions.ResourceNotFoundException;
import br.com.ibpt.mappers.v1.VersionMapper;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.repositories.v1.VersionCustomRepository;
import br.com.ibpt.repositories.v1.VersionRepository;

@Service
public class VersionService {

	@Autowired
	private VersionRepository versionRepository;
	
	@Autowired
	private VersionCustomRepository versionCustomRepository;
	
	@Autowired
	private VersionMapper versionMapper;
	
	public VersionVO findById(Integer id) {
		Version entity = versionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		
		return versionMapper.toVersionVO(entity);
	}
	
	public List<VersionVO> findCustom(String name, String effectivePeriodMonth, String effectivePeriodYear) {
		
		List<Version> entityList = versionCustomRepository.findCustom(name, effectivePeriodMonth, effectivePeriodYear);
		
		return versionMapper.toVersionVOList(entityList);
	}
	
	public VersionVO create(VersionVO vo) {
		if (vo == null) throw new RequiredObjectIsNullException();
		
		Version persisted = versionRepository.save(versionMapper.toVersion(vo));
		
		return versionMapper.toVersionVO(persisted);
	}
	
	public VersionVO updateById(VersionVO vo, Integer id) {
		if (vo == null) throw new RequiredObjectIsNullException();
		
		Version entity = versionRepository.findById(vo.getKey()).orElseThrow(() -> new ResourceNotFoundException("No records found for this id!"));
		entity.setName(vo.getName());
		entity.setEffectivePeriodUntil(vo.getEffectivePeriodUntil());
		
		return versionMapper.toVersionVO(versionRepository.save(entity));
	}
	
}
