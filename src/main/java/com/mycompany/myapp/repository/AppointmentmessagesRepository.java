package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Appointmentmessages;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Appointmentmessages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentmessagesRepository extends JpaRepository<Appointmentmessages, Long> {

}
