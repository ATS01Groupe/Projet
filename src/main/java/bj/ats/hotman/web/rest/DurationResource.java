package bj.ats.hotman.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.ats.hotman.domain.Duration;
import bj.ats.hotman.service.DurationService;
import bj.ats.hotman.web.rest.util.HeaderUtil;
import bj.ats.hotman.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Duration.
 */
@RestController
@RequestMapping("/api")
public class DurationResource {

    private final Logger log = LoggerFactory.getLogger(DurationResource.class);

    private static final String ENTITY_NAME = "duration";
        
    private final DurationService durationService;

    public DurationResource(DurationService durationService) {
        this.durationService = durationService;
    }

    /**
     * POST  /durations : Create a new duration.
     *
     * @param duration the duration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new duration, or with status 400 (Bad Request) if the duration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/durations")
    @Timed
    public ResponseEntity<Duration> createDuration(@Valid @RequestBody Duration duration) throws URISyntaxException {
        log.debug("REST request to save Duration : {}", duration);
        if (duration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new duration cannot already have an ID")).body(null);
        }
        Duration result = durationService.save(duration);
        return ResponseEntity.created(new URI("/api/durations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /durations : Updates an existing duration.
     *
     * @param duration the duration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated duration,
     * or with status 400 (Bad Request) if the duration is not valid,
     * or with status 500 (Internal Server Error) if the duration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/durations")
    @Timed
    public ResponseEntity<Duration> updateDuration(@Valid @RequestBody Duration duration) throws URISyntaxException {
        log.debug("REST request to update Duration : {}", duration);
        if (duration.getId() == null) {
            return createDuration(duration);
        }
        Duration result = durationService.save(duration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, duration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /durations : get all the durations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of durations in body
     */
    @GetMapping("/durations")
    @Timed
    public ResponseEntity<List<Duration>> getAllDurations(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Durations");
        Page<Duration> page = durationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/durations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /durations/:id : get the "id" duration.
     *
     * @param id the id of the duration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the duration, or with status 404 (Not Found)
     */
    @GetMapping("/durations/{id}")
    @Timed
    public ResponseEntity<Duration> getDuration(@PathVariable Long id) {
        log.debug("REST request to get Duration : {}", id);
        Duration duration = durationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(duration));
    }

    /**
     * DELETE  /durations/:id : delete the "id" duration.
     *
     * @param id the id of the duration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/durations/{id}")
    @Timed
    public ResponseEntity<Void> deleteDuration(@PathVariable Long id) {
        log.debug("REST request to delete Duration : {}", id);
        durationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/durations?query=:query : search for the duration corresponding
     * to the query.
     *
     * @param query the query of the duration search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/durations")
    @Timed
    public ResponseEntity<List<Duration>> searchDurations(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Durations for query {}", query);
        Page<Duration> page = durationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/durations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
