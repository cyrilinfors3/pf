package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Projectmessages;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Projectmessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectmessagesRepository extends JpaRepository<Projectmessages, Long> {

}
