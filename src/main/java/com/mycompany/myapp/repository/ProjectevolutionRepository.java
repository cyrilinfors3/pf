package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projectevolution;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Projectevolution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectevolutionRepository extends JpaRepository<Projectevolution, Long> {

}
