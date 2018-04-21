package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Projectmessages;

import com.mycompany.myapp.repository.ProjectmessagesRepository;
import com.mycompany.myapp.repository.search.ProjectmessagesSearchRepository;
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
 * REST controller for managing Projectmessages.
 */
@RestController
@RequestMapping("/api")
public class ProjectmessagesResource {

    private final Logger log = LoggerFactory.getLogger(ProjectmessagesResource.class);

    private static final String ENTITY_NAME = "projectmessages";

    private final ProjectmessagesRepository projectmessagesRepository;

    private final ProjectmessagesSearchRepository projectmessagesSearchRepository;

    public ProjectmessagesResource(ProjectmessagesRepository projectmessagesRepository, ProjectmessagesSearchRepository projectmessagesSearchRepository) {
        this.projectmessagesRepository = projectmessagesRepository;
        this.projectmessagesSearchRepository = projectmessagesSearchRepository;
    }

    /**
     * POST  /projectmessages : Create a new projectmessages.
     *
     * @param projectmessages the projectmessages to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectmessages, or with status 400 (Bad Request) if the projectmessages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projectmessages")
    @Timed
    public ResponseEntity<Projectmessages> createProjectmessages(@RequestBody Projectmessages projectmessages) throws URISyntaxException {
        log.debug("REST request to save Projectmessages : {}", projectmessages);
        if (projectmessages.getId() != null) {
            throw new BadRequestAlertException("A new projectmessages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projectmessages result = projectmessagesRepository.save(projectmessages);
        projectmessagesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/projectmessages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectmessages : Updates an existing projectmessages.
     *
     * @param projectmessages the projectmessages to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectmessages,
     * or with status 400 (Bad Request) if the projectmessages is not valid,
     * or with status 500 (Internal Server Error) if the projectmessages couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projectmessages")
    @Timed
    public ResponseEntity<Projectmessages> updateProjectmessages(@RequestBody Projectmessages projectmessages) throws URISyntaxException {
        log.debug("REST request to update Projectmessages : {}", projectmessages);
        if (projectmessages.getId() == null) {
            return createProjectmessages(projectmessages);
        }
        Projectmessages result = projectmessagesRepository.save(projectmessages);
        projectmessagesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectmessages.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectmessages : get all the projectmessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectmessages in body
     */
    @GetMapping("/projectmessages")
    @Timed
    public ResponseEntity<List<Projectmessages>> getAllProjectmessages(Pageable pageable) {
        log.debug("REST request to get a page of Projectmessages");
        Page<Projectmessages> page = projectmessagesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projectmessages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projectmessages/:id : get the "id" projectmessages.
     *
     * @param id the id of the projectmessages to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectmessages, or with status 404 (Not Found)
     */
    @GetMapping("/projectmessages/{id}")
    @Timed
    public ResponseEntity<Projectmessages> getProjectmessages(@PathVariable Long id) {
        log.debug("REST request to get Projectmessages : {}", id);
        Projectmessages projectmessages = projectmessagesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectmessages));
    }

    /**
     * DELETE  /projectmessages/:id : delete the "id" projectmessages.
     *
     * @param id the id of the projectmessages to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projectmessages/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectmessages(@PathVariable Long id) {
        log.debug("REST request to delete Projectmessages : {}", id);
        projectmessagesRepository.delete(id);
        projectmessagesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/projectmessages?query=:query : search for the projectmessages corresponding
     * to the query.
     *
     * @param query the query of the projectmessages search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/projectmessages")
    @Timed
    public ResponseEntity<List<Projectmessages>> searchProjectmessages(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Projectmessages for query {}", query);
        Page<Projectmessages> page = projectmessagesSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/projectmessages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
