package bj.ats.hotman.service;

import bj.ats.hotman.domain.Rooms;
import java.util.List;

/**
 * Service Interface for managing Rooms.
 */
public interface RoomsService {

    /**
     * Save a rooms.
     *
     * @param rooms the entity to save
     * @return the persisted entity
     */
    Rooms save(Rooms rooms);

    /**
     *  Get all the rooms.
     *  
     *  @return the list of entities
     */
    List<Rooms> findAll();
    /**
     *  Get all the RoomsDTO where Client is null.
     *
     *  @return the list of entities
     */
    List<Rooms> findAllWhereClientIsNull();

    /**
     *  Get the "id" rooms.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Rooms findOne(Long id);

    /**
     *  Delete the "id" rooms.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rooms corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Rooms> search(String query);
}
