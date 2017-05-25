package bj.ats.hotman.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.ats.hotman.domain.Badge;
import bj.ats.hotman.service.BadgeService;
import bj.ats.hotman.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Badge.
 */
@RestController
@RequestMapping("/api")
public class BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "badge";
        
    private final BadgeService badgeService;

    public BadgeResource(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    /**
     * POST  /badges : Create a new badge.
     *
     * @param badge the badge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new badge, or with status 400 (Bad Request) if the badge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/badges")
    @Timed
    public ResponseEntity<Badge> createBadge(@Valid @RequestBody Badge badge) throws URISyntaxException {
        log.debug("REST request to save Badge : {}", badge);
        if (badge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new badge cannot already have an ID")).body(null);
        }
        Badge result = badgeService.save(badge);
        return ResponseEntity.created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /badges : Updates an existing badge.
     *
     * @param badge the badge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated badge,
     * or with status 400 (Bad Request) if the badge is not valid,
     * or with status 500 (Internal Server Error) if the badge couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/badges")
    @Timed
    public ResponseEntity<Badge> updateBadge(@Valid @RequestBody Badge badge) throws URISyntaxException {
        log.debug("REST request to update Badge : {}", badge);
        if (badge.getId() == null) {
            return createBadge(badge);
        }
        Badge result = badgeService.save(badge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, badge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /badges : get all the badges.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of badges in body
     */
    @GetMapping("/badges")
    @Timed
    public List<Badge> getAllBadges(@RequestParam(required = false) String filter) {
        if ("rooms-is-null".equals(filter)) {
            log.debug("REST request to get all Badges where rooms is null");
            return badgeService.findAllWhereRoomsIsNull();
        }
        log.debug("REST request to get all Badges");
        return badgeService.findAll();
    }

    /**
     * GET  /badges/:id : get the "id" badge.
     *
     * @param id the id of the badge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the badge, or with status 404 (Not Found)
     */
    @GetMapping("/badges/{id}")
    @Timed
    public ResponseEntity<Badge> getBadge(@PathVariable Long id) {
        log.debug("REST request to get Badge : {}", id);
        Badge badge = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(badge));
    }

    /**
     * DELETE  /badges/:id : delete the "id" badge.
     *
     * @param id the id of the badge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/badges/{id}")
    @Timed
    public ResponseEntity<Void> deleteBadge(@PathVariable Long id) {
        log.debug("REST request to delete Badge : {}", id);
        badgeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/badges?query=:query : search for the badge corresponding
     * to the query.
     *
     * @param query the query of the badge search 
     * @return the result of the search
     */
    @GetMapping("/_search/badges")
    @Timed
    public List<Badge> searchBadges(@RequestParam String query) {
        log.debug("REST request to search Badges for query {}", query);
        return badgeService.search(query);
    }


}
