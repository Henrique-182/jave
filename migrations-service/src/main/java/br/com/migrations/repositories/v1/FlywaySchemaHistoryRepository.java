package br.com.migrations.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.migrations.model.v1.FlywaySchemaHistory;

@Repository
public interface FlywaySchemaHistoryRepository extends JpaRepository<FlywaySchemaHistory, Integer> {

}
