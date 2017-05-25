package bj.ats.hotman.web.rest;

import bj.ats.hotman.HotManApp;

import bj.ats.hotman.domain.Rooms;
import bj.ats.hotman.repository.RoomsRepository;
import bj.ats.hotman.service.RoomsService;
import bj.ats.hotman.repository.search.RoomsSearchRepository;
import bj.ats.hotman.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import bj.ats.hotman.domain.enumeration.Typedroom;
import bj.ats.hotman.domain.enumeration.Etatromms;
/**
 * Test class for the RoomsResource REST controller.
 *
 * @see RoomsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class RoomsResourceIntTest {

    private static final String DEFAULT_ROMSNUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ROMSNUMBER = "BBBBBBBBBB";

    private static final Typedroom DEFAULT_TYPEROOM = Typedroom.VIP;
    private static final Typedroom UPDATED_TYPEROOM = Typedroom.SIMPLE;

    private static final Etatromms DEFAULT_ETAT = Etatromms.DISPONIPLE;
    private static final Etatromms UPDATED_ETAT = Etatromms.OCCUPE;

    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private RoomsSearchRepository roomsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoomsMockMvc;

    private Rooms rooms;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RoomsResource roomsResource = new RoomsResource(roomsService);
        this.restRoomsMockMvc = MockMvcBuilders.standaloneSetup(roomsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rooms createEntity(EntityManager em) {
        Rooms rooms = new Rooms()
            .romsnumber(DEFAULT_ROMSNUMBER)
            .typeroom(DEFAULT_TYPEROOM)
            .etat(DEFAULT_ETAT);
        return rooms;
    }

    @Before
    public void initTest() {
        roomsSearchRepository.deleteAll();
        rooms = createEntity(em);
    }

    @Test
    @Transactional
    public void createRooms() throws Exception {
        int databaseSizeBeforeCreate = roomsRepository.findAll().size();

        // Create the Rooms
        restRoomsMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isCreated());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeCreate + 1);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRomsnumber()).isEqualTo(DEFAULT_ROMSNUMBER);
        assertThat(testRooms.getTyperoom()).isEqualTo(DEFAULT_TYPEROOM);
        assertThat(testRooms.getEtat()).isEqualTo(DEFAULT_ETAT);

        // Validate the Rooms in Elasticsearch
        Rooms roomsEs = roomsSearchRepository.findOne(testRooms.getId());
        assertThat(roomsEs).isEqualToComparingFieldByField(testRooms);
    }

    @Test
    @Transactional
    public void createRoomsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomsRepository.findAll().size();

        // Create the Rooms with an existing ID
        rooms.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomsMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRomsnumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setRomsnumber(null);

        // Create the Rooms, which fails.

        restRoomsMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTyperoomIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setTyperoom(null);

        // Create the Rooms, which fails.

        restRoomsMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomsRepository.findAll().size();
        // set the field null
        rooms.setEtat(null);

        // Create the Rooms, which fails.

        restRoomsMockMvc.perform(post("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isBadRequest());

        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get all the roomsList
        restRoomsMockMvc.perform(get("/api/rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rooms.getId().intValue())))
            .andExpect(jsonPath("$.[*].romsnumber").value(hasItem(DEFAULT_ROMSNUMBER.toString())))
            .andExpect(jsonPath("$.[*].typeroom").value(hasItem(DEFAULT_TYPEROOM.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void getRooms() throws Exception {
        // Initialize the database
        roomsRepository.saveAndFlush(rooms);

        // Get the rooms
        restRoomsMockMvc.perform(get("/api/rooms/{id}", rooms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rooms.getId().intValue()))
            .andExpect(jsonPath("$.romsnumber").value(DEFAULT_ROMSNUMBER.toString()))
            .andExpect(jsonPath("$.typeroom").value(DEFAULT_TYPEROOM.toString()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRooms() throws Exception {
        // Get the rooms
        restRoomsMockMvc.perform(get("/api/rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRooms() throws Exception {
        // Initialize the database
        roomsService.save(rooms);

        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // Update the rooms
        Rooms updatedRooms = roomsRepository.findOne(rooms.getId());
        updatedRooms
            .romsnumber(UPDATED_ROMSNUMBER)
            .typeroom(UPDATED_TYPEROOM)
            .etat(UPDATED_ETAT);

        restRoomsMockMvc.perform(put("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRooms)))
            .andExpect(status().isOk());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate);
        Rooms testRooms = roomsList.get(roomsList.size() - 1);
        assertThat(testRooms.getRomsnumber()).isEqualTo(UPDATED_ROMSNUMBER);
        assertThat(testRooms.getTyperoom()).isEqualTo(UPDATED_TYPEROOM);
        assertThat(testRooms.getEtat()).isEqualTo(UPDATED_ETAT);

        // Validate the Rooms in Elasticsearch
        Rooms roomsEs = roomsSearchRepository.findOne(testRooms.getId());
        assertThat(roomsEs).isEqualToComparingFieldByField(testRooms);
    }

    @Test
    @Transactional
    public void updateNonExistingRooms() throws Exception {
        int databaseSizeBeforeUpdate = roomsRepository.findAll().size();

        // Create the Rooms

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoomsMockMvc.perform(put("/api/rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rooms)))
            .andExpect(status().isCreated());

        // Validate the Rooms in the database
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRooms() throws Exception {
        // Initialize the database
        roomsService.save(rooms);

        int databaseSizeBeforeDelete = roomsRepository.findAll().size();

        // Get the rooms
        restRoomsMockMvc.perform(delete("/api/rooms/{id}", rooms.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean roomsExistsInEs = roomsSearchRepository.exists(rooms.getId());
        assertThat(roomsExistsInEs).isFalse();

        // Validate the database is empty
        List<Rooms> roomsList = roomsRepository.findAll();
        assertThat(roomsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRooms() throws Exception {
        // Initialize the database
        roomsService.save(rooms);

        // Search the rooms
        restRoomsMockMvc.perform(get("/api/_search/rooms?query=id:" + rooms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rooms.getId().intValue())))
            .andExpect(jsonPath("$.[*].romsnumber").value(hasItem(DEFAULT_ROMSNUMBER.toString())))
            .andExpect(jsonPath("$.[*].typeroom").value(hasItem(DEFAULT_TYPEROOM.toString())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rooms.class);
        Rooms rooms1 = new Rooms();
        rooms1.setId(1L);
        Rooms rooms2 = new Rooms();
        rooms2.setId(rooms1.getId());
        assertThat(rooms1).isEqualTo(rooms2);
        rooms2.setId(2L);
        assertThat(rooms1).isNotEqualTo(rooms2);
        rooms1.setId(null);
        assertThat(rooms1).isNotEqualTo(rooms2);
    }
}
