package bj.ats.hotman.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.ats.hotman.domain.Rooms;
import bj.ats.hotman.service.RoomsService;
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
 * REST controller for managing Rooms.
 */
@RestController
@RequestMapping("/api")
public class RoomsResource {

    private final Logger log = LoggerFactory.getLogger(RoomsResource.class);

    private static final String ENTITY_NAME = "rooms";
        
    private final RoomsService roomsService;

    public RoomsResource(RoomsService roomsService) {
        this.roomsService = roomsService;
    }

    /**
     * POST  /rooms : Create a new rooms.
     *
     * @param rooms the rooms to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rooms, or with status 400 (Bad Request) if the rooms has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rooms")
    @Timed
    public ResponseEntity<Rooms> createRooms(@Valid @RequestBody Rooms rooms) throws URISyntaxException {
        log.debug("REST request to save Rooms : {}", rooms);
        if (rooms.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new rooms cannot already have an ID")).body(null);
        }
        Rooms result = roomsService.save(rooms);
        return ResponseEntity.created(new URI("/api/rooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rooms : Updates an existing rooms.
     *
     * @param rooms the rooms to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rooms,
     * or with status 400 (Bad Request) if the rooms is not valid,
     * or with status 500 (Internal Server Error) if the rooms couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rooms")
    @Timed
    public ResponseEntity<Rooms> updateRooms(@Valid @RequestBody Rooms rooms) throws URISyntaxException {
        log.debug("REST request to update Rooms : {}", rooms);
        if (rooms.getId() == null) {
            return createRooms(rooms);
        }
        Rooms result = roomsService.save(rooms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rooms.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rooms : get all the rooms.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of rooms in body
     */
    @GetMapping("/rooms")
    @Timed
    public List<Rooms> getAllRooms(@RequestParam(required = false) String filter) {
        if ("client-is-null".equals(filter)) {
            log.debug("REST request to get all Roomss where client is null");
            return roomsService.findAllWhereClientIsNull();
        }
        log.debug("REST request to get all Rooms");
        return roomsService.findAll();
    }

    /**
     * GET  /rooms/:id : get the "id" rooms.
     *
     * @param id the id of the rooms to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rooms, or with status 404 (Not Found)
     */
    @GetMapping("/rooms/{id}")
    @Timed
    public ResponseEntity<Rooms> getRooms(@PathVariable Long id) {
        log.debug("REST request to get Rooms : {}", id);
        Rooms rooms = roomsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rooms));
    }

    /**
     * DELETE  /rooms/:id : delete the "id" rooms.
     *
     * @param id the id of the rooms to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteRooms(@PathVariable Long id) {
        log.debug("REST request to delete Rooms : {}", id);
        roomsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rooms?query=:query : search for the rooms corresponding
     * to the query.
     *
     * @param query the query of the rooms search 
     * @return the result of the search
     */
    @GetMapping("/_search/rooms")
    @Timed
    public List<Rooms> searchRooms(@RequestParam String query) {
        log.debug("REST request to search Rooms for query {}", query);
        return roomsService.search(query);
    }


}
