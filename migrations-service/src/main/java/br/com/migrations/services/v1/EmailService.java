package br.com.migrations.services.v1;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.migrations.data.vo.v1.EmailVO;
import br.com.migrations.model.v1.FlywaySchemaHistory;
import br.com.migrations.model.v1.ServiceName;
import br.com.migrations.proxys.v1.EmailProxy;
import br.com.migrations.repositories.v1.FlywaySchemaHistoryRepository;

@Service
public class EmailService {

	@Autowired
	private EmailProxy proxy;
	
	@Autowired
	private FlywaySchemaHistoryRepository repository;
	
	public void sendAll(String emailTo, Boolean isHtml) {
		EmailVO data = new EmailVO();
		
		data.setEmailTo(emailTo);
		
		data.setSubject("Flyway Migrations History - Jave Datebase");
		
		String content = createOrderedListContent(repository.findAll());
		data.setContent(content);
		
		data.setServiceName(ServiceName.Migrations);
		
		proxy.getEmail(data, isHtml);
	}
	
	private static String createOrderedListContent(List<FlywaySchemaHistory> list) {
		String content = "<ol>";
		
		for (FlywaySchemaHistory entity : list) {
			String date = new SimpleDateFormat("dd/MM/yyyy").format(entity.getInstalledOn());

			content += "<li>" + date + " - " + entity.getScript() + "</li>";
		}
		
		content += "</ol>";
		
		return content;
	}
	
}
