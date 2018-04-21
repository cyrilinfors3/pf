package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Appointmentmessages;

import com.mycompany.myapp.repository.AppointmentmessagesRepository;
import com.mycompany.myapp.repository.search.AppointmentmessagesSearchRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Appointmentmessages.
 */
@RestController
@RequestMapping("/api")
public class AppointmentmessagesResource {

    private final Logger log = LoggerFactory.getLogger(AppointmentmessagesResource.class);

    private static final String ENTITY_NAME = "appointmentmessages";

    private final AppointmentmessagesRepository appointmentmessagesRepository;

    private final AppointmentmessagesSearchRepository appointmentmessagesSearchRepository;

    public AppointmentmessagesResource(AppointmentmessagesRepository appointmentmessagesRepository, AppointmentmessagesSearchRepository appointmentmessagesSearchRepository) {
        this.appointmentmessagesRepository = appointmentmessagesRepository;
        this.appointmentmessagesSearchRepository = appointmentmessagesSearchRepository;
    }

    /**
     * POST  /appointmentmessages : Create a new appointmentmessages.
     *
     * @param appointmentmessages the appointmentmessages to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appointmentmessages, or with status 400 (Bad Request) if the appointmentmessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/appointmentmessages")
    @Timed
    public ResponseEntity<Appointmentmessages> createAppointmentmessages(@RequestBody Appointmentmessages appointmentmessages) throws URISyntaxException {
        log.debug("REST request to save Appointmentmessages : {}", appointmentmessages);
        if (appointmentmessages.getId() != null) {
            throw new BadRequestAlertException("A new appointmentmessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Appointmentmessages result = appointmentmessagesRepository.save(appointmentmessages);
        appointmentmessagesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/appointmentmessages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /appointmentmessages : Updates an existing appointmentmessages.
     *
     * @param appointmentmessages the appointmentmessages to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appointmentmessages,
     * or with status 400 (Bad Request) if the appointmentmessages is not valid,
     * or with status 500 (Internal Server Error) if the appointmentmessages couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/appointmentmessages")
    @Timed
    public ResponseEntity<Appointmentmessages> updateAppointmentmessages(@RequestBody Appointmentmessages appointmentmessages) throws URISyntaxException {
        log.debug("REST request to update Appointmentmessages : {}", appointmentmessages);
        if (appointmentmessages.getId() == null) {
            return createAppointmentmessages(appointmentmessages);
        }
        Appointmentmessages result = appointmentmessagesRepository.save(appointmentmessages);
        appointmentmessagesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appointmentmessages.getId().toString()))
            .body(result);
    }

    /**
     * GET  /appointmentmessages : get all the appointmentmessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of appointmentmessages in body
     */
    @GetMapping("/appointmentmessages")
    @Timed
    public ResponseEntity<List<Appointmentmessages>> getAllAppointmentmessages(Pageable pageable) {
        log.debug("REST request to get a page of Appointmentmessages");
        Page<Appointmentmessages> page = appointmentmessagesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/appointmentmessages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /appointmentmessages/:id : get the "id" appointmentmessages.
     *
     * @param id the id of the appointmentmessages to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appointmentmessages, or with status 404 (Not Found)
     */
    @GetMapping("/appointmentmessages/{id}")
    @Timed
    public ResponseEntity<Appointmentmessages> getAppointmentmessages(@PathVariable Long id) {
        log.debug("REST request to get Appointmentmessages : {}", id);
        Appointmentmessages appointmentmessages = appointmentmessagesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appointmentmessages));
    }

    /**
     * DELETE  /appointmentmessages/:id : delete the "id" appointmentmessages.
     *
     * @param id the id of the appointmentmessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/appointmentmessages/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppointmentmessages(@PathVariable Long id) {
        log.debug("REST request to delete Appointmentmessages : {}", id);
        appointmentmessagesRepository.delete(id);
        appointmentmessagesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/appointmentmessages?query=:query : search for the appointmentmessages corresponding
     * to the query.
     *
     * @param query the query of the appointmentmessages search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/appointmentmessages")
    @Timed
    public ResponseEntity<List<Appointmentmessages>> searchAppointmentmessages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Appointmentmessages for query {}", query);
        Page<Appointmentmessages> page = appointmentmessagesSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/appointmentmessages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
