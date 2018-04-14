package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Userextra;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Userextra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserextraRepository extends JpaRepository<Userextra, Long> {

}
