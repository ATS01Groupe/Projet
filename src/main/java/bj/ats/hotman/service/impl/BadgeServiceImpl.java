package bj.ats.hotman.service.impl;

import bj.ats.hotman.service.BadgeService;
import bj.ats.hotman.domain.Badge;
import bj.ats.hotman.repository.BadgeRepository;
import bj.ats.hotman.repository.search.BadgeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Badge.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService{

    private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);
    
    private final BadgeRepository badgeRepository;

    private final BadgeSearchRepository badgeSearchRepository;

    public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeSearchRepository badgeSearchRepository) {
        this.badgeRepository = badgeRepository;
        this.badgeSearchRepository = badgeSearchRepository;
    }

    /**
     * Save a badge.
     *
     * @param badge the entity to save
     * @return the persisted entity
     */
    @Override
    public Badge save(Badge badge) {
        log.debug("Request to save Badge : {}", badge);
        Badge result = badgeRepository.save(badge);
        badgeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the badges.
     *  
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Badge> findAll() {
        log.debug("Request to get all Badges");
        List<Badge> result = badgeRepository.findAll();

        return result;
    }


    /**
     *  get all the badges where Rooms is null.
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<Badge> findAllWhereRoomsIsNull() {
        log.debug("Request to get all badges where Rooms is null");
        return StreamSupport
            .stream(badgeRepository.findAll().spliterator(), false)
            .filter(badge -> badge.getRooms() == null)
            .collect(Collectors.toList());
    }

    /**
     *  Get one badge by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Badge findOne(Long id) {
        log.debug("Request to get Badge : {}", id);
        Badge badge = badgeRepository.findOne(id);
        return badge;
    }

    /**
     *  Delete the  badge by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Badge : {}", id);
        badgeRepository.delete(id);
        badgeSearchRepository.delete(id);
    }

    /**
     * Search for the badge corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Badge> search(String query) {
        log.debug("Request to search Badges for query {}", query);
        return StreamSupport
            .stream(badgeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
