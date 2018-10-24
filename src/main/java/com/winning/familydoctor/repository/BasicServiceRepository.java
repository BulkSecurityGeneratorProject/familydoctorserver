package com.winning.familydoctor.repository;

import com.winning.familydoctor.domain.BasicService;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the BasicService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BasicServiceRepository extends JpaRepository<BasicService, Long>, JpaSpecificationExecutor<BasicService> {

}
