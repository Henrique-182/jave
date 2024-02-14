package br.com.ibpt.services.v1;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ibpt.data.vo.v1.EmailVO;
import br.com.ibpt.model.v1.CompanyIbpt;
import br.com.ibpt.model.v1.Ibpt;
import br.com.ibpt.model.v1.ServiceName;
import br.com.ibpt.model.v1.Version;
import br.com.ibpt.proxys.v1.EmailProxy;
import br.com.ibpt.repositories.v1.IbptRepository;
import br.com.ibpt.repositories.v1.VersionRepository;

@Service
public class EmailService {
	
	@Autowired
	private EmailProxy proxy;

	@Autowired
	private VersionRepository versionRepository;
	
	@Autowired
	private IbptRepository ibptRepository;
	
	public void sendAllVersions(String emailTo, Boolean isHtml) {
		EmailVO data = new EmailVO();
		
		data.setEmailTo(emailTo);
		
		data.setSubject("All Versions - Database Jave - Schemas IBPT");
		
		String content = createdOrderedListVersion(versionRepository.findAll());
		data.setContent(content);
		
		data.setServiceName(ServiceName.Ibpt);
		
		proxy.sendEmail(data, isHtml);
	}
	
	public void sendAllIbpts(String emailTo, Boolean isHtml, String versionName) {
		EmailVO data = new EmailVO();
		
		data.setEmailTo(emailTo);
		
		data.setSubject("All Ibpts - Database Jave - Schemas IBPT");
		
		String content = createdOrderedListIbpt(ibptRepository.findByVersionName(versionName));
		data.setContent(content);
		
		data.setServiceName(ServiceName.Ibpt);
		
		proxy.sendEmail(data, isHtml);
	}
	
	private static String createdOrderedListVersion(List<Version> list) {
		String content = "<ol>";
		
		for (Version version : list) {
			String dateFrom = new SimpleDateFormat("dd/MM/yyyy").format(version.getEffectivePeriodFrom());
			String dateTo = new SimpleDateFormat("dd/MM/yyyy").format(version.getEffectivePeriodUntil());
			
			
			content += "<li>" + version.getName() + " (" + dateFrom + " - " + dateTo + ")</li>";
		}
		
		content += "</ol>";
		
		return content;
	}
	
	private static String createdOrderedListIbpt(List<Ibpt> list) {
		String content = "<ol>";
		
		for (Ibpt ibpt : list) {
			CompanyIbpt company = ibpt.getCompanySoftware().getCompany();
			
			content += "<li ";
			if (ibpt.getIsUpdated()) {
				content += "style='color: green;'>";
			} else {
				content += "style='color: red;'>";
			}
			content += ibpt.getVersion().getName() + " - " + company.getCnpj() + " - " + company.getTradeName() + "</li>";
		}
		
		content += "</ol>";
		
		return content;
	}

}
