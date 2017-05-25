package bj.ats.hotman.service.impl;

import bj.ats.hotman.service.RoomsService;
import bj.ats.hotman.domain.Rooms;
import bj.ats.hotman.repository.RoomsRepository;
import bj.ats.hotman.repository.search.RoomsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Rooms.
 */
@Service
@Transactional
public class RoomsServiceImpl implements RoomsService{

    private final Logger log = LoggerFactory.getLogger(RoomsServiceImpl.class);
    
    private final RoomsRepository roomsRepository;

    private final RoomsSearchRepository roomsSearchRepository;

    public RoomsServiceImpl(RoomsRepository roomsRepository, RoomsSearchRepository roomsSearchRepository) {
        this.roomsRepository = roomsRepository;
        this.roomsSearchRepository = roomsSearchRepository;
    }

    /**
     * Save a rooms.
     *
     * @param rooms the entity to save
     * @return the persisted entity
     */
    @Override
    public Rooms save(Rooms rooms) {
        log.debug("Request to save Rooms : {}", rooms);
        Rooms result = roomsRepository.save(rooms);
        roomsSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the rooms.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rooms> findAll() {
        log.debug("Request to get all Rooms");
        List<Rooms> result = roomsRepository.findAll();

        return result;
    }


    /**
     *  get all the rooms where Client is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Rooms> findAllWhereClientIsNull() {
        log.debug("Request to get all rooms where Client is null");
        return StreamSupport
            .stream(roomsRepository.findAll().spliterator(), false)
            .filter(rooms -> rooms.getClient() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one rooms by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Rooms findOne(Long id) {
        log.debug("Request to get Rooms : {}", id);
        Rooms rooms = roomsRepository.findOne(id);
        return rooms;
    }

    /**
     *  Delete the  rooms by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rooms : {}", id);
        roomsRepository.delete(id);
        roomsSearchRepository.delete(id);
    }

    /**
     * Search for the rooms corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rooms> search(String query) {
        log.debug("Request to search Rooms for query {}", query);
        return StreamSupport
            .stream(roomsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
