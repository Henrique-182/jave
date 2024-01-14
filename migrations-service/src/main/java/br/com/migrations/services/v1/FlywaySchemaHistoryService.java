package br.com.migrations.services.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.migrations.model.v1.FlywaySchemaHistory;
import br.com.migrations.repositories.v1.FlywaySchemaHistoryRepository;

@Service
public class FlywaySchemaHistoryService {

	@Autowired
	private FlywaySchemaHistoryRepository repository;
	
	public List<FlywaySchemaHistory> findAll() {
		return repository.findAll();
	}
	
}
