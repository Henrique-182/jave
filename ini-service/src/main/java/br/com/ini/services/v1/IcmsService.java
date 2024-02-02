package br.com.ini.services.v1;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
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
import br.com.ini.data.vo.v1.IniVO;
import br.com.ini.data.vo.v1.SectionVO;
import br.com.ini.exceptions.v1.FileStorageException;
import br.com.ini.exceptions.v1.MyFileNotFoundException;
import br.com.ini.utils.v1.ServiceUtil;

@Service
public class IcmsService {
	
	@Autowired
	private FileStorageService service;

	public String storeFile(String folder, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		return service.storeFile(folder, file);
	}
	
	public Resource downloadFile(String folder, String filename) {
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		return service.loadFileAsResource(folder, filename);
	}
	
	public IniVO fileToJson(String folder, String filename) {
		
		Resource originalFileResource = service.loadFileAsResource(folder, filename);
		
		Ini ini = new Ini();
		URL url = null;
		
		try {
			url = originalFileResource.getURL();
			ini.load(url);
		} catch (Exception e) {
			new MyFileNotFoundException("File URL not found!", e);
		}
		
		IniVO data = addSectionsToVO(ini);
		
		return data;
	}
	
	public void jsonToFile(String folder, IniVO data) {
		
		Resource originalFileResource = service.loadFileAsResource(folder, data.getFilename());
		
		try {
			String processedFolder = originalFileResource.getFile().getParent() + "\\Processed\\";

			ServiceUtil.createDirectories(Path.of(processedFolder));
			File newFile = new File(processedFolder + data.getFilename());
			
			if (newFile.exists()) newFile.delete();
			newFile.createNewFile();
			
			addSectionsToFile(newFile, data);
			
		} catch (Exception e) {
			new FileStorageException("It was not possible to convert json into file!", e);
		}
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
	
	private void addSectionsToFile(File file, IniVO data) {
		
		List<SectionVO> sectionsList = data.getSections();
		
		for (int i = 0; i < sectionsList.size(); i++) {
			String currentSection = sectionsList.get(i).getName();
			
			ServiceUtil.writeToFile(file, "[" + currentSection + "]");
			
			List<AtributeVO> atributes = data.getSections().get(i).getAtributes();
			
			for (AtributeVO atribute : atributes) {
				String key = atribute.getKey();
				String value = atribute.getValue();
				
				ServiceUtil.writeToFile(file, key + "=" + value);
			}
		}
	}
	
}
