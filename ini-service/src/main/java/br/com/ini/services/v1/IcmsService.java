package br.com.ini.services.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.ini4j.Ini;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ini.data.vo.v1.AtributeVO;
import br.com.ini.data.vo.v1.FileStorageResponseVO;
import br.com.ini.data.vo.v1.FileVO;
import br.com.ini.data.vo.v1.IniVO;
import br.com.ini.data.vo.v1.SectionVO;
import br.com.ini.exceptions.v1.FileStorageException;
import br.com.ini.exceptions.v1.MyFileNotFoundException;
import br.com.ini.models.v1.ProjectName;
import br.com.ini.models.v1.ServiceName;
import br.com.ini.proxys.v1.FileProxy;
import br.com.ini.utils.v1.ServiceUtil;

@Service
public class IcmsService {
	
	@Autowired
	private FileProxy proxy;

	public FileStorageResponseVO uploadFile(MultipartFile file, String identifier) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		FileStorageResponseVO data = proxy.uploadFile(ServiceName.Ini, ProjectName.Icms, identifier, false, file);
		
		return data;
	}
	
	public Resource downloadFile(String identifier, Boolean isProcessed, String filename) {
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		Resource resource = proxy.getFile(ServiceName.Ini, ProjectName.Icms, identifier, isProcessed, filename);
		
		return resource;
	}
	
	public IniVO fileToJson(String identifier, String filename) {
		
		Resource originalFileResource = proxy.getFile(ServiceName.Ini, ProjectName.Icms, identifier, false, filename);
		
		Ini ini = new Ini();
		
		try {
			ini.load(originalFileResource.getInputStream());
		} catch (Exception e) {
			new MyFileNotFoundException("It was not possible to get File Input Stream!", e);
		}
		
		IniVO data = addSectionsToVO(ini);
		data.setCnpj(identifier);
		data.setFilename(filename);
		
		return data;
	}
	
	public void jsonToFile(IniVO data) {
		
		FileVO fileVO = new FileVO();
		
		addSectionsToList(fileVO, data);
		
		proxy.uploadFile(ServiceName.Ini, ProjectName.Icms, data.getCnpj(), true, data.getFilename(), fileVO);
	}
	
	
	private IniVO addSectionsToVO(Ini ini) {
		IniVO iniVO = new IniVO();
		
		Set<String> sectionsSet = ini.keySet();
		
		for (String currentSection : sectionsSet) {
			SectionVO section = new SectionVO();
			section.setName(currentSection);
			section.setAtributes(addAtributesToVO(ini, currentSection));
			
			iniVO.addSectionVO(section);
		}
		
		return iniVO;
	}
	
	
	private List<AtributeVO> addAtributesToVO(Ini ini, String currentSection) {
		List<AtributeVO> atributes = new ArrayList<>();
		
		var keyArray = ini.get(currentSection).keySet().toArray();
		
		for (Object key : keyArray) {
			AtributeVO atr = new AtributeVO();
			
			atr.setKey((String) key);
			atr.setValue(ini.get(currentSection, key));
			
			atributes.add(atr);
		}
		
		return atributes;
	}
	
	
	private void addSectionsToList(FileVO fileVO, IniVO data) {
		
		List<SectionVO> sectionsList = data.getSections();
		
		for (int i = 0; i < sectionsList.size(); i++) {
			SectionVO currentSection = sectionsList.get(i);
			
			fileVO.addLineVO("[" + currentSection.getName() + "]");
			
			List<AtributeVO> atributes = currentSection.getAtributes();
			
			for (AtributeVO atribute : atributes) fileVO.addLineVO(atribute.getKey() + "=" + atribute.getValue());
			
		}
	}
	
	
}
