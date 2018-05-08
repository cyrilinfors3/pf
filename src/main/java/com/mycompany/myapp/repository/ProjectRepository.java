package com.mycompany.myapp.repository;

import java.util.List;

import com.mycompany.myapp.domain.Project;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	List<Project> findByOwner(String currentUserLogin);
 

	List<Project> findAllByOwner(String currentUserLogin);


	Page<Project> findByOwner(String l, Pageable pageable); 

}
