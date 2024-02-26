package br.com.ibpt.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.ibpt.data.vo.v1.FileStorageResponseVO;
import br.com.ibpt.exceptions.v1.FileStorageException;
import br.com.ibpt.model.v1.ProjectName;
import br.com.ibpt.model.v1.ServiceName;
import br.com.ibpt.proxys.v1.FileProxy;
import br.com.ibpt.utils.v1.ServiceUtil;

@Service
public class FileService {
	
	@Autowired
	private FileProxy proxy;

	public FileStorageResponseVO uploadFile(String identifier, MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		FileStorageResponseVO data = proxy.uploadFile(ServiceName.Ibpt, ProjectName.Zip, identifier, false, file);
		
		return data;
	}
	
	public Resource downloadFile(String identifier, String filename) {
		
		if (ServiceUtil.checkIfFilenameIsInvalid(filename)) throw new FileStorageException("Sorry! Filename contains invalid path sequence (" + filename + ")");
		
		Resource resource = proxy.getFile(ServiceName.Ibpt, ProjectName.Zip, identifier, false, filename);
		
		return resource;
	}
	
}
