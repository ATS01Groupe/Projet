package bj.ats.hotman.service.impl;

import bj.ats.hotman.service.DurationService;
import bj.ats.hotman.domain.Duration;
import bj.ats.hotman.repository.DurationRepository;
import bj.ats.hotman.repository.search.DurationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Duration.
 */
@Service
@Transactional
public class DurationServiceImpl implements DurationService{

    private final Logger log = LoggerFactory.getLogger(DurationServiceImpl.class);
    
    private final DurationRepository durationRepository;

    private final DurationSearchRepository durationSearchRepository;

    public DurationServiceImpl(DurationRepository durationRepository, DurationSearchRepository durationSearchRepository) {
        this.durationRepository = durationRepository;
        this.durationSearchRepository = durationSearchRepository;
    }

    /**
     * Save a duration.
     *
     * @param duration the entity to save
     * @return the persisted entity
     */
    @Override
    public Duration save(Duration duration) {
        log.debug("Request to save Duration : {}", duration);
        Duration result = durationRepository.save(duration);
        durationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the durations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Duration> findAll(Pageable pageable) {
        log.debug("Request to get all Durations");
        Page<Duration> result = durationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one duration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Duration findOne(Long id) {
        log.debug("Request to get Duration : {}", id);
        Duration duration = durationRepository.findOne(id);
        return duration;
    }

    /**
     *  Delete the  duration by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Duration : {}", id);
        durationRepository.delete(id);
        durationSearchRepository.delete(id);
    }

    /**
     * Search for the duration corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Duration> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Durations for query {}", query);
        Page<Duration> result = durationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
