package bj.ats.hotman.service;

import bj.ats.hotman.domain.Badge;
import java.util.List;

/**
 * Service Interface for managing Badge.
 */
public interface BadgeService {

    /**
     * Save a badge.
     *
     * @param badge the entity to save
     * @return the persisted entity
     */
    Badge save(Badge badge);

    /**
     *  Get all the badges.
     *  
     *  @return the list of entities
     */
    List<Badge> findAll();
    /**
     *  Get all the BadgeDTO where Rooms is null.
     *
     *  @return the list of entities
     */
    List<Badge> findAllWhereRoomsIsNull();

    /**
     *  Get the "id" badge.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Badge findOne(Long id);

    /**
     *  Delete the "id" badge.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the badge corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Badge> search(String query);
}
