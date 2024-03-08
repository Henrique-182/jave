package br.com.mail.services.v1;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import br.com.mail.configs.v1.EmailConfig;
import br.com.mail.data.vo.v1.EmailVO;
import br.com.mail.mappers.v1.EmailMapper;
import br.com.mail.model.v1.Email;
import br.com.mail.model.v1.EmailStatus;
import br.com.mail.repositories.v1.EmailRepository;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private EmailRepository repository;
	
	@Autowired
	private EmailMapper mapper;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailConfig config;
	
	@SuppressWarnings("finally")
	public Email send(EmailVO data, Boolean isHtml) {
		
		Email email = mapper.toEntity(data);
		email.setEmailFrom(config.getUsername());
		email.setLastUpdateDatetime(new Date());
		email.setCreationDatetime(new Date());
		
		try {
			MimeMessage message = mailSender.createMimeMessage();
			 
			message.setSubject(email.getSubject());
			
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(email.getEmailFrom());
			helper.setTo(email.getEmailTo());
			helper.setText(email.getContent(), isHtml);
			
			mailSender.send(message);
			
		} catch (Exception e) {
			email.setStatus(EmailStatus.Error);
		} finally {
			email.setStatus(EmailStatus.Sent);
			return repository.save(email);
		}
	}
	
}
