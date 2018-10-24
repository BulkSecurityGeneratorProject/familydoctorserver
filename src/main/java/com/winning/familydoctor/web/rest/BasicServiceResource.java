package com.winning.familydoctor.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.winning.familydoctor.domain.BasicService;
import com.winning.familydoctor.service.BasicServiceService;
import com.winning.familydoctor.web.rest.errors.BadRequestAlertException;
import com.winning.familydoctor.web.rest.util.HeaderUtil;
import com.winning.familydoctor.web.rest.util.PaginationUtil;
import com.winning.familydoctor.service.dto.BasicServiceCriteria;
import com.winning.familydoctor.service.BasicServiceQueryService;
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

/**
 * REST controller for managing BasicService.
 */
@RestController
@RequestMapping("/api")
public class BasicServiceResource {

    private final Logger log = LoggerFactory.getLogger(BasicServiceResource.class);

    private static final String ENTITY_NAME = "basicService";

    private BasicServiceService basicServiceService;

    private BasicServiceQueryService basicServiceQueryService;

    public BasicServiceResource(BasicServiceService basicServiceService, BasicServiceQueryService basicServiceQueryService) {
        this.basicServiceService = basicServiceService;
        this.basicServiceQueryService = basicServiceQueryService;
    }

    /**
     * POST  /basic-services : Create a new basicService.
     *
     * @param basicService the basicService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new basicService, or with status 400 (Bad Request) if the basicService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/basic-services")
    @Timed
    public ResponseEntity<BasicService> createBasicService(@Valid @RequestBody BasicService basicService) throws URISyntaxException {
        log.debug("REST request to save BasicService : {}", basicService);
        if (basicService.getId() != null) {
            throw new BadRequestAlertException("A new basicService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BasicService result = basicServiceService.save(basicService);
        return ResponseEntity.created(new URI("/api/basic-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /basic-services : Updates an existing basicService.
     *
     * @param basicService the basicService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated basicService,
     * or with status 400 (Bad Request) if the basicService is not valid,
     * or with status 500 (Internal Server Error) if the basicService couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/basic-services")
    @Timed
    public ResponseEntity<BasicService> updateBasicService(@Valid @RequestBody BasicService basicService) throws URISyntaxException {
        log.debug("REST request to update BasicService : {}", basicService);
        if (basicService.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BasicService result = basicServiceService.save(basicService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, basicService.getId().toString()))
            .body(result);
    }

    @GetMapping("/basic-services")
    @Timed
    public ResponseEntity<Page<BasicService>> getAllBasicServices(Pageable pageable) {
        Page<BasicService> page = basicServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/basic-services");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    /**
     * GET  /basic-services : get all the basicServices.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of basicServices in body
     */
    @GetMapping("/basic-services/search")
    @Timed
    public ResponseEntity<Page<BasicService>> getAllBasicServices(BasicServiceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get BasicServices by criteria: {}", criteria);
        Page<BasicService> page = basicServiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/basic-services");
        return new ResponseEntity<>(page, headers, HttpStatus.OK);
    }

    /**
    * GET  /basic-services/count : count all the basicServices.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/basic-services/count")
    @Timed
    public ResponseEntity<Long> countBasicServices(BasicServiceCriteria criteria) {
        log.debug("REST request to count BasicServices by criteria: {}", criteria);
        return ResponseEntity.ok().body(basicServiceQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /basic-services/:id : get the "id" basicService.
     *
     * @param id the id of the basicService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the basicService, or with status 404 (Not Found)
     */
    @GetMapping("/basic-services/{id}")
    @Timed
    public ResponseEntity<BasicService> getBasicService(@PathVariable Long id) {
        log.debug("REST request to get BasicService : {}", id);
        Optional<BasicService> basicService = basicServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(basicService);
    }

    /**
     * DELETE  /basic-services/:id : delete the "id" basicService.
     *
     * @param id the id of the basicService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/basic-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteBasicService(@PathVariable Long id) {
        log.debug("REST request to delete BasicService : {}", id);
        basicServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
