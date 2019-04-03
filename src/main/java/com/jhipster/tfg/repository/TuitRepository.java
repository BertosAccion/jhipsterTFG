package com.jhipster.tfg.repository;

import com.jhipster.tfg.domain.Tuit;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Tuit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TuitRepository extends JpaRepository<Tuit, Long> {

}
