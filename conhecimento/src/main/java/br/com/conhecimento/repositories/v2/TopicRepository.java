package br.com.conhecimento.repositories.v2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.conhecimento.model.v2.Topic;

public interface TopicRepository extends JpaRepository<Topic, Integer>, PagingAndSortingRepository<Topic, Integer> {

	Page<Topic> findPageableByNameContaining(@Param("topicName") String topicName, Pageable pageable);
	
}
