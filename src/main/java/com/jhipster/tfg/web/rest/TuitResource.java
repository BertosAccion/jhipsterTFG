package com.jhipster.tfg.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipster.tfg.domain.Tuit;
import com.jhipster.tfg.repository.TuitRepository;
import com.jhipster.tfg.web.rest.errors.BadRequestAlertException;
import com.jhipster.tfg.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Tuit.
 */
@RestController
@RequestMapping("/api")
public class TuitResource {

    private final Logger log = LoggerFactory.getLogger(TuitResource.class);

    private static final String ENTITY_NAME = "tuit";

    private final TuitRepository tuitRepository;

    public TuitResource(TuitRepository tuitRepository) {
        this.tuitRepository = tuitRepository;
    }

    /**
     * POST  /tuits : Create a new tuit.
     *
     * @param tuit the tuit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tuit, or with status 400 (Bad Request) if the tuit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tuits")
    @Timed
    public ResponseEntity<Tuit> createTuit(@Valid @RequestBody Tuit tuit) throws URISyntaxException {
        log.debug("REST request to save Tuit : {}", tuit);
        if (tuit.getId() != null) {
            throw new BadRequestAlertException("A new tuit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tuit result = tuitRepository.save(tuit);
        return ResponseEntity.created(new URI("/api/tuits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tuits : Updates an existing tuit.
     *
     * @param tuit the tuit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tuit,
     * or with status 400 (Bad Request) if the tuit is not valid,
     * or with status 500 (Internal Server Error) if the tuit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tuits")
    @Timed
    public ResponseEntity<Tuit> updateTuit(@Valid @RequestBody Tuit tuit) throws URISyntaxException {
        log.debug("REST request to update Tuit : {}", tuit);
        if (tuit.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tuit result = tuitRepository.save(tuit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tuit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tuits : get all the tuits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tuits in body
     */
    @GetMapping("/tuits")
    @Timed
    public List<Tuit> getAllTuits() {
        log.debug("REST request to get all Tuits");
        return tuitRepository.findAll();
    }

    /**
     * GET  /tuits/:id : get the "id" tuit.
     *
     * @param id the id of the tuit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tuit, or with status 404 (Not Found)
     */
    @GetMapping("/tuits/{id}")
    @Timed
    public ResponseEntity<Tuit> getTuit(@PathVariable Long id) {
        log.debug("REST request to get Tuit : {}", id);
        Optional<Tuit> tuit = tuitRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tuit);
    }

    /**
     * DELETE  /tuits/:id : delete the "id" tuit.
     *
     * @param id the id of the tuit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tuits/{id}")
    @Timed
    public ResponseEntity<Void> deleteTuit(@PathVariable Long id) {
        log.debug("REST request to delete Tuit : {}", id);

        tuitRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
