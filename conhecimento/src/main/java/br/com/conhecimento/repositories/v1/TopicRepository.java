package br.com.conhecimento.repositories.v1;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.conhecimento.model.v1.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
