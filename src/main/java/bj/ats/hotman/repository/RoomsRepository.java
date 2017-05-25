package bj.ats.hotman.repository;

import bj.ats.hotman.domain.Rooms;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Rooms entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomsRepository extends JpaRepository<Rooms,Long> {

}
