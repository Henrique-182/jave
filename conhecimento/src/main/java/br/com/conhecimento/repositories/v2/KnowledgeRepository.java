package br.com.conhecimento.repositories.v2;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.conhecimento.model.v2.Knowledge;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Integer>, PagingAndSortingRepository<Knowledge, Integer> {

	Page<Knowledge> findPageableByTitleContainingAndDescriptionContainingAndContentContaining(
		@Param("knowledgeTitle") String knowledgeTitle, 
		@Param("knowledgeDescription") String knowledgeDescription, 
		@Param("knowledgeContent") String knowledgeContent, 
		Pageable pageable
	);
	
}
