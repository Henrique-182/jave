package br.com.sistemas.repositories.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sistemas.model.v1.Software;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Integer>, PagingAndSortingRepository<Software, Integer> {

	Page<Software> findPageableByNameContaining(@Param("softwareName") String softwareName, Pageable pageable);
	
}
