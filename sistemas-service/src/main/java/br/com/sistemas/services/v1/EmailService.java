package br.com.sistemas.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sistemas.data.vo.v1.EmailVO;
import br.com.sistemas.model.v1.Company;
import br.com.sistemas.model.v1.CompanySoftware;
import br.com.sistemas.model.v1.ServiceName;
import br.com.sistemas.proxys.v1.EmailProxy;
import br.com.sistemas.repositories.v1.CompanyRepository;

@Service
public class EmailService {
	
	@Autowired
	private EmailProxy proxy;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public void sendAllCompanies(String emailTo, Boolean isHtml) {
		EmailVO data = new EmailVO();
		
		data.setEmailTo(emailTo);
		
		data.setSubject("All Companies - Datebase Jave - Schema Sistemas");
		
		String content = createOrderedListCompanies(companyRepository.findAll());
		data.setContent(content);
		
		data.setServiceName(ServiceName.Migrations);
		
		proxy.sendEmail(data, isHtml);
	}
	
	private static String createOrderedListCompanies(List<Company> list) {
		String content = "<ol>";
		
		for (Company company : list) {
			content += "<li>";
			content += "<a href='http://localhost:8181/v1/company/" + company.getId() + "' target='_blank'>";
			content += company.getCnpj(); 
			content += "</a>";
			content += " - " + company.getTradeName();
			content += "</li>";
			content += createUnorderedListCompanySoftware(company);
		}
		
		content += "</ol>";
		
		return content;
	}
	
	private static String createUnorderedListCompanySoftware(Company company) {
		String content = "<ul>";
		
		List<CompanySoftware> list = company.getSoftwares();
		
		for (CompanySoftware entity : list) {
			content += "<li>" + entity.getType() + " - " + entity.getSoftware().getName() + " - Ativo (" + entity.getIsActive() + ")</li>";
		}
		
		content += "</ul>";
		
		return content;
	}
	
}
