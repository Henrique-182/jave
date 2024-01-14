package br.com.migrations.unittests.mocks.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.migrations.model.v1.FlywaySchemaHistory;

public class FlywaySchemaHistoryMock {

	public FlywaySchemaHistory entity() {
		return entity(0);
	}
	
	public List<FlywaySchemaHistory> entityList() {
		List<FlywaySchemaHistory> list = new ArrayList<>();
		
		for(int i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public FlywaySchemaHistory entity(Integer number) {
		FlywaySchemaHistory entity = new FlywaySchemaHistory();
		entity.setInstalleRank(number);
		entity.setVersion("Version" + number);
		entity.setDescription("Description" + number);
		entity.setType("Type" + number);
		entity.setScript("Script" + number);
		entity.setChecksum(number);
		entity.setInstalledBy("Installed By" + number);
		entity.setInstalledOn(new Date(number));
		entity.setExecutionTime(number);
		entity.setSuccess(true);
		
		return entity;
	}
	
 }
