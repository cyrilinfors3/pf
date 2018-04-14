package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Projectevolution;

import com.mycompany.myapp.repository.ProjectevolutionRepository;
import com.mycompany.myapp.repository.search.ProjectevolutionSearchRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Projectevolution.
 */
@RestController
@RequestMapping("/api")
public class ProjectevolutionResource {

    private final Logger log = LoggerFactory.getLogger(ProjectevolutionResource.class);

    private static final String ENTITY_NAME = "projectevolution";

    private final ProjectevolutionRepository projectevolutionRepository;

    private final ProjectevolutionSearchRepository projectevolutionSearchRepository;

    public ProjectevolutionResource(ProjectevolutionRepository projectevolutionRepository, ProjectevolutionSearchRepository projectevolutionSearchRepository) {
        this.projectevolutionRepository = projectevolutionRepository;
        this.projectevolutionSearchRepository = projectevolutionSearchRepository;
    }

    /**
     * POST  /projectevolutions : Create a new projectevolution.
     *
     * @param projectevolution the projectevolution to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectevolution, or with status 400 (Bad Request) if the projectevolution has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/projectevolutions")
    @Timed
    public ResponseEntity<Projectevolution> createProjectevolution(@Valid @RequestBody Projectevolution projectevolution) throws URISyntaxException {
        log.debug("REST request to save Projectevolution : {}", projectevolution);
        if (projectevolution.getId() != null) {
            throw new BadRequestAlertException("A new projectevolution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projectevolution result = projectevolutionRepository.save(projectevolution);
        projectevolutionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/projectevolutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /projectevolutions : Updates an existing projectevolution.
     *
     * @param projectevolution the projectevolution to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectevolution,
     * or with status 400 (Bad Request) if the projectevolution is not valid,
     * or with status 500 (Internal Server Error) if the projectevolution couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/projectevolutions")
    @Timed
    public ResponseEntity<Projectevolution> updateProjectevolution(@Valid @RequestBody Projectevolution projectevolution) throws URISyntaxException {
        log.debug("REST request to update Projectevolution : {}", projectevolution);
        if (projectevolution.getId() == null) {
            return createProjectevolution(projectevolution);
        }
        Projectevolution result = projectevolutionRepository.save(projectevolution);
        projectevolutionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectevolution.getId().toString()))
            .body(result);
    }

    /**
     * GET  /projectevolutions : get all the projectevolutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectevolutions in body
     */
    @GetMapping("/projectevolutions")
    @Timed
    public ResponseEntity<List<Projectevolution>> getAllProjectevolutions(Pageable pageable) {
        log.debug("REST request to get a page of Projectevolutions");
        Page<Projectevolution> page = projectevolutionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/projectevolutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /projectevolutions/:id : get the "id" projectevolution.
     *
     * @param id the id of the projectevolution to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectevolution, or with status 404 (Not Found)
     */
    @GetMapping("/projectevolutions/{id}")
    @Timed
    public ResponseEntity<Projectevolution> getProjectevolution(@PathVariable Long id) {
        log.debug("REST request to get Projectevolution : {}", id);
        Projectevolution projectevolution = projectevolutionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectevolution));
    }

    /**
     * DELETE  /projectevolutions/:id : delete the "id" projectevolution.
     *
     * @param id the id of the projectevolution to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/projectevolutions/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectevolution(@PathVariable Long id) {
        log.debug("REST request to delete Projectevolution : {}", id);
        projectevolutionRepository.delete(id);
        projectevolutionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/projectevolutions?query=:query : search for the projectevolution corresponding
     * to the query.
     *
     * @param query the query of the projectevolution search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/projectevolutions")
    @Timed
    public ResponseEntity<List<Projectevolution>> searchProjectevolutions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Projectevolutions for query {}", query);
        Page<Projectevolution> page = projectevolutionSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/projectevolutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
