package bj.ats.hotman.web.rest;

import bj.ats.hotman.HotManApp;

import bj.ats.hotman.domain.Groupe;
import bj.ats.hotman.repository.GroupeRepository;
import bj.ats.hotman.service.GroupeService;
import bj.ats.hotman.repository.search.GroupeSearchRepository;
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

/**
 * Test class for the GroupeResource REST controller.
 *
 * @see GroupeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HotManApp.class)
public class GroupeResourceIntTest {

    private static final String DEFAULT_NAMEGROUPE = "AAAAAAAAAA";
    private static final String UPDATED_NAMEGROUPE = "BBBBBBBBBB";

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private GroupeService groupeService;

    @Autowired
    private GroupeSearchRepository groupeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGroupeMockMvc;

    private Groupe groupe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GroupeResource groupeResource = new GroupeResource(groupeService);
        this.restGroupeMockMvc = MockMvcBuilders.standaloneSetup(groupeResource)
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
    public static Groupe createEntity(EntityManager em) {
        Groupe groupe = new Groupe()
            .namegroupe(DEFAULT_NAMEGROUPE);
        return groupe;
    }

    @Before
    public void initTest() {
        groupeSearchRepository.deleteAll();
        groupe = createEntity(em);
    }

    @Test
    @Transactional
    public void createGroupe() throws Exception {
        int databaseSizeBeforeCreate = groupeRepository.findAll().size();

        // Create the Groupe
        restGroupeMockMvc.perform(post("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isCreated());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeCreate + 1);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getNamegroupe()).isEqualTo(DEFAULT_NAMEGROUPE);

        // Validate the Groupe in Elasticsearch
        Groupe groupeEs = groupeSearchRepository.findOne(testGroupe.getId());
        assertThat(groupeEs).isEqualToComparingFieldByField(testGroupe);
    }

    @Test
    @Transactional
    public void createGroupeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = groupeRepository.findAll().size();

        // Create the Groupe with an existing ID
        groupe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeMockMvc.perform(post("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGroupes() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList
        restGroupeMockMvc.perform(get("/api/groupes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].namegroupe").value(hasItem(DEFAULT_NAMEGROUPE.toString())));
    }

    @Test
    @Transactional
    public void getGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get the groupe
        restGroupeMockMvc.perform(get("/api/groupes/{id}", groupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(groupe.getId().intValue()))
            .andExpect(jsonPath("$.namegroupe").value(DEFAULT_NAMEGROUPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGroupe() throws Exception {
        // Get the groupe
        restGroupeMockMvc.perform(get("/api/groupes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGroupe() throws Exception {
        // Initialize the database
        groupeService.save(groupe);

        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Update the groupe
        Groupe updatedGroupe = groupeRepository.findOne(groupe.getId());
        updatedGroupe
            .namegroupe(UPDATED_NAMEGROUPE);

        restGroupeMockMvc.perform(put("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGroupe)))
            .andExpect(status().isOk());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getNamegroupe()).isEqualTo(UPDATED_NAMEGROUPE);

        // Validate the Groupe in Elasticsearch
        Groupe groupeEs = groupeSearchRepository.findOne(testGroupe.getId());
        assertThat(groupeEs).isEqualToComparingFieldByField(testGroupe);
    }

    @Test
    @Transactional
    public void updateNonExistingGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Create the Groupe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGroupeMockMvc.perform(put("/api/groupes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isCreated());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGroupe() throws Exception {
        // Initialize the database
        groupeService.save(groupe);

        int databaseSizeBeforeDelete = groupeRepository.findAll().size();

        // Get the groupe
        restGroupeMockMvc.perform(delete("/api/groupes/{id}", groupe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean groupeExistsInEs = groupeSearchRepository.exists(groupe.getId());
        assertThat(groupeExistsInEs).isFalse();

        // Validate the database is empty
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchGroupe() throws Exception {
        // Initialize the database
        groupeService.save(groupe);

        // Search the groupe
        restGroupeMockMvc.perform(get("/api/_search/groupes?query=id:" + groupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].namegroupe").value(hasItem(DEFAULT_NAMEGROUPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Groupe.class);
        Groupe groupe1 = new Groupe();
        groupe1.setId(1L);
        Groupe groupe2 = new Groupe();
        groupe2.setId(groupe1.getId());
        assertThat(groupe1).isEqualTo(groupe2);
        groupe2.setId(2L);
        assertThat(groupe1).isNotEqualTo(groupe2);
        groupe1.setId(null);
        assertThat(groupe1).isNotEqualTo(groupe2);
    }
}
