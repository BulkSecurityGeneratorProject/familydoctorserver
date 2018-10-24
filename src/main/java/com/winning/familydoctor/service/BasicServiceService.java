package com.winning.familydoctor.service;

import com.winning.familydoctor.domain.BasicService;
import com.winning.familydoctor.repository.BasicServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing BasicService.
 */
@Service
@Transactional
public class BasicServiceService {

    private final Logger log = LoggerFactory.getLogger(BasicServiceService.class);

    private BasicServiceRepository basicServiceRepository;

    public BasicServiceService(BasicServiceRepository basicServiceRepository) {
        this.basicServiceRepository = basicServiceRepository;
    }

    /**
     * Save a basicService.
     *
     * @param basicService the entity to save
     * @return the persisted entity
     */
    public BasicService save(BasicService basicService) {
        log.debug("Request to save BasicService : {}", basicService);
        return basicServiceRepository.save(basicService);
    }

    /**
     * Get all the basicServices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BasicService> findAll(Pageable pageable) {
        log.debug("Request to get all BasicServices");
        return basicServiceRepository.findAll(pageable);
    }


    /**
     * Get one basicService by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<BasicService> findOne(Long id) {
        log.debug("Request to get BasicService : {}", id);
        return basicServiceRepository.findById(id);
    }

    /**
     * Delete the basicService by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BasicService : {}", id);
        basicServiceRepository.deleteById(id);
    }
}
