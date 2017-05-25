package bj.ats.hotman.service;

import bj.ats.hotman.domain.Groupe;
import java.util.List;

/**
 * Service Interface for managing Groupe.
 */
public interface GroupeService {

    /**
     * Save a groupe.
     *
     * @param groupe the entity to save
     * @return the persisted entity
     */
    Groupe save(Groupe groupe);

    /**
     *  Get all the groupes.
     *  
     *  @return the list of entities
     */
    List<Groupe> findAll();

    /**
     *  Get the "id" groupe.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Groupe findOne(Long id);

    /**
     *  Delete the "id" groupe.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the groupe corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @return the list of entities
     */
    List<Groupe> search(String query);
}
