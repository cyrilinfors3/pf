package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Userextra;

import com.mycompany.myapp.repository.UserextraRepository;
import com.mycompany.myapp.repository.search.UserextraSearchRepository;
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
 * REST controller for managing Userextra.
 */
@RestController
@RequestMapping("/api")
public class UserextraResource {

    private final Logger log = LoggerFactory.getLogger(UserextraResource.class);

    private static final String ENTITY_NAME = "userextra";

    private final UserextraRepository userextraRepository;

    private final UserextraSearchRepository userextraSearchRepository;

    public UserextraResource(UserextraRepository userextraRepository, UserextraSearchRepository userextraSearchRepository) {
        this.userextraRepository = userextraRepository;
        this.userextraSearchRepository = userextraSearchRepository;
    }

    /**
     * POST  /userextras : Create a new userextra.
     *
     * @param userextra the userextra to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userextra, or with status 400 (Bad Request) if the userextra has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/userextras")
    @Timed
    public ResponseEntity<Userextra> createUserextra(@RequestBody Userextra userextra) throws URISyntaxException {
        log.debug("REST request to save Userextra : {}", userextra);
        if (userextra.getId() != null) {
            throw new BadRequestAlertException("A new userextra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Userextra result = userextraRepository.save(userextra);
        userextraSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/userextras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userextras : Updates an existing userextra.
     *
     * @param userextra the userextra to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userextra,
     * or with status 400 (Bad Request) if the userextra is not valid,
     * or with status 500 (Internal Server Error) if the userextra couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/userextras")
    @Timed
    public ResponseEntity<Userextra> updateUserextra(@RequestBody Userextra userextra) throws URISyntaxException {
        log.debug("REST request to update Userextra : {}", userextra);
        if (userextra.getId() == null) {
            return createUserextra(userextra);
        }
        Userextra result = userextraRepository.save(userextra);
        userextraSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userextra.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userextras : get all the userextras.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userextras in body
     */
    @GetMapping("/userextras")
    @Timed
    public ResponseEntity<List<Userextra>> getAllUserextras(Pageable pageable) {
        log.debug("REST request to get a page of Userextras");
        Page<Userextra> page = userextraRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/userextras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /userextras/:id : get the "id" userextra.
     *
     * @param id the id of the userextra to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userextra, or with status 404 (Not Found)
     */
    @GetMapping("/userextras/{id}")
    @Timed
    public ResponseEntity<Userextra> getUserextra(@PathVariable Long id) {
        log.debug("REST request to get Userextra : {}", id);
        Userextra userextra = userextraRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userextra));
    }

    /**
     * DELETE  /userextras/:id : delete the "id" userextra.
     *
     * @param id the id of the userextra to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/userextras/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserextra(@PathVariable Long id) {
        log.debug("REST request to delete Userextra : {}", id);
        userextraRepository.delete(id);
        userextraSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/userextras?query=:query : search for the userextra corresponding
     * to the query.
     *
     * @param query the query of the userextra search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/userextras")
    @Timed
    public ResponseEntity<List<Userextra>> searchUserextras(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Userextras for query {}", query);
        Page<Userextra> page = userextraSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/userextras");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
