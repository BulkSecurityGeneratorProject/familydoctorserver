package com.winning.familydoctor.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.winning.familydoctor.domain.BasicService;
import com.winning.familydoctor.domain.*; // for static metamodels
import com.winning.familydoctor.repository.BasicServiceRepository;
import com.winning.familydoctor.service.dto.BasicServiceCriteria;

/**
 * Service for executing complex queries for BasicService entities in the database.
 * The main input is a {@link BasicServiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BasicService} or a {@link Page} of {@link BasicService} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BasicServiceQueryService extends QueryService<BasicService> {

    private final Logger log = LoggerFactory.getLogger(BasicServiceQueryService.class);

    private BasicServiceRepository basicServiceRepository;

    public BasicServiceQueryService(BasicServiceRepository basicServiceRepository) {
        this.basicServiceRepository = basicServiceRepository;
    }

    /**
     * Return a {@link List} of {@link BasicService} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BasicService> findByCriteria(BasicServiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BasicService> specification = createSpecification(criteria);
        return basicServiceRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BasicService} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BasicService> findByCriteria(BasicServiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BasicService> specification = createSpecification(criteria);
        return basicServiceRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BasicServiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BasicService> specification = createSpecification(criteria);
        return basicServiceRepository.count(specification);
    }

    /**
     * Function to convert BasicServiceCriteria to a {@link Specification}
     */
    private Specification<BasicService> createSpecification(BasicServiceCriteria criteria) {
        Specification<BasicService> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), BasicService_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), BasicService_.name));
            }
            if (criteria.getSearchNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSearchNo(), BasicService_.searchNo));
            }
            if (criteria.getServiceType() != null) {
                specification = specification.and(buildSpecification(criteria.getServiceType(), BasicService_.serviceType));
            }
            if (criteria.getAppraiseType() != null) {
                specification = specification.and(buildSpecification(criteria.getAppraiseType(), BasicService_.appraiseType));
            }
            if (criteria.getAppraiseValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppraiseValue(), BasicService_.appraiseValue));
            }
            if (criteria.getReferencePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferencePrice(), BasicService_.referencePrice));
            }
            if (criteria.getSubsidyPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSubsidyPrice(), BasicService_.subsidyPrice));
            }
            if (criteria.getServiceCount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getServiceCount(), BasicService_.serviceCount));
            }
            if (criteria.getCanAppointment() != null) {
                specification = specification.and(buildSpecification(criteria.getCanAppointment(), BasicService_.canAppointment));
            }
            if (criteria.getOrder() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrder(), BasicService_.order));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), BasicService_.status));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), BasicService_.desc));
            }
        }
        return specification;
    }
}
