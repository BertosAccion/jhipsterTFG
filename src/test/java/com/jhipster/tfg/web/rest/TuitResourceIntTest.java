package com.jhipster.tfg.web.rest;

import com.jhipster.tfg.TfgTest1App;

import com.jhipster.tfg.domain.Tuit;
import com.jhipster.tfg.repository.TuitRepository;
import com.jhipster.tfg.web.rest.errors.ExceptionTranslator;

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


import static com.jhipster.tfg.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TuitResource REST controller.
 *
 * @see TuitResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TfgTest1App.class)
public class TuitResourceIntTest {

    private static final String DEFAULT_USER_EMISOR = "AAAAAAAAAA";
    private static final String UPDATED_USER_EMISOR = "BBBBBBBBBB";

    private static final String DEFAULT_USER_RECEPTOR = "AAAAAAAAAA";
    private static final String UPDATED_USER_RECEPTOR = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO_TUIT = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO_TUIT = "BBBBBBBBBB";

    @Autowired
    private TuitRepository tuitRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTuitMockMvc;

    private Tuit tuit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TuitResource tuitResource = new TuitResource(tuitRepository);
        this.restTuitMockMvc = MockMvcBuilders.standaloneSetup(tuitResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tuit createEntity(EntityManager em) {
        Tuit tuit = new Tuit()
            .userEmisor(DEFAULT_USER_EMISOR)
            .userReceptor(DEFAULT_USER_RECEPTOR)
            .textoTuit(DEFAULT_TEXTO_TUIT);
        return tuit;
    }

    @Before
    public void initTest() {
        tuit = createEntity(em);
    }

    @Test
    @Transactional
    public void createTuit() throws Exception {
        int databaseSizeBeforeCreate = tuitRepository.findAll().size();

        // Create the Tuit
        restTuitMockMvc.perform(post("/api/tuits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tuit)))
            .andExpect(status().isCreated());

        // Validate the Tuit in the database
        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeCreate + 1);
        Tuit testTuit = tuitList.get(tuitList.size() - 1);
        assertThat(testTuit.getUserEmisor()).isEqualTo(DEFAULT_USER_EMISOR);
        assertThat(testTuit.getUserReceptor()).isEqualTo(DEFAULT_USER_RECEPTOR);
        assertThat(testTuit.getTextoTuit()).isEqualTo(DEFAULT_TEXTO_TUIT);
    }

    @Test
    @Transactional
    public void createTuitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tuitRepository.findAll().size();

        // Create the Tuit with an existing ID
        tuit.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTuitMockMvc.perform(post("/api/tuits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tuit)))
            .andExpect(status().isBadRequest());

        // Validate the Tuit in the database
        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUserEmisorIsRequired() throws Exception {
        int databaseSizeBeforeTest = tuitRepository.findAll().size();
        // set the field null
        tuit.setUserEmisor(null);

        // Create the Tuit, which fails.

        restTuitMockMvc.perform(post("/api/tuits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tuit)))
            .andExpect(status().isBadRequest());

        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextoTuitIsRequired() throws Exception {
        int databaseSizeBeforeTest = tuitRepository.findAll().size();
        // set the field null
        tuit.setTextoTuit(null);

        // Create the Tuit, which fails.

        restTuitMockMvc.perform(post("/api/tuits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tuit)))
            .andExpect(status().isBadRequest());

        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTuits() throws Exception {
        // Initialize the database
        tuitRepository.saveAndFlush(tuit);

        // Get all the tuitList
        restTuitMockMvc.perform(get("/api/tuits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tuit.getId().intValue())))
            .andExpect(jsonPath("$.[*].userEmisor").value(hasItem(DEFAULT_USER_EMISOR.toString())))
            .andExpect(jsonPath("$.[*].userReceptor").value(hasItem(DEFAULT_USER_RECEPTOR.toString())))
            .andExpect(jsonPath("$.[*].textoTuit").value(hasItem(DEFAULT_TEXTO_TUIT.toString())));
    }
    
    @Test
    @Transactional
    public void getTuit() throws Exception {
        // Initialize the database
        tuitRepository.saveAndFlush(tuit);

        // Get the tuit
        restTuitMockMvc.perform(get("/api/tuits/{id}", tuit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tuit.getId().intValue()))
            .andExpect(jsonPath("$.userEmisor").value(DEFAULT_USER_EMISOR.toString()))
            .andExpect(jsonPath("$.userReceptor").value(DEFAULT_USER_RECEPTOR.toString()))
            .andExpect(jsonPath("$.textoTuit").value(DEFAULT_TEXTO_TUIT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTuit() throws Exception {
        // Get the tuit
        restTuitMockMvc.perform(get("/api/tuits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTuit() throws Exception {
        // Initialize the database
        tuitRepository.saveAndFlush(tuit);

        int databaseSizeBeforeUpdate = tuitRepository.findAll().size();

        // Update the tuit
        Tuit updatedTuit = tuitRepository.findById(tuit.getId()).get();
        // Disconnect from session so that the updates on updatedTuit are not directly saved in db
        em.detach(updatedTuit);
        updatedTuit
            .userEmisor(UPDATED_USER_EMISOR)
            .userReceptor(UPDATED_USER_RECEPTOR)
            .textoTuit(UPDATED_TEXTO_TUIT);

        restTuitMockMvc.perform(put("/api/tuits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTuit)))
            .andExpect(status().isOk());

        // Validate the Tuit in the database
        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeUpdate);
        Tuit testTuit = tuitList.get(tuitList.size() - 1);
        assertThat(testTuit.getUserEmisor()).isEqualTo(UPDATED_USER_EMISOR);
        assertThat(testTuit.getUserReceptor()).isEqualTo(UPDATED_USER_RECEPTOR);
        assertThat(testTuit.getTextoTuit()).isEqualTo(UPDATED_TEXTO_TUIT);
    }

    @Test
    @Transactional
    public void updateNonExistingTuit() throws Exception {
        int databaseSizeBeforeUpdate = tuitRepository.findAll().size();

        // Create the Tuit

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTuitMockMvc.perform(put("/api/tuits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tuit)))
            .andExpect(status().isBadRequest());

        // Validate the Tuit in the database
        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTuit() throws Exception {
        // Initialize the database
        tuitRepository.saveAndFlush(tuit);

        int databaseSizeBeforeDelete = tuitRepository.findAll().size();

        // Get the tuit
        restTuitMockMvc.perform(delete("/api/tuits/{id}", tuit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tuit> tuitList = tuitRepository.findAll();
        assertThat(tuitList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tuit.class);
        Tuit tuit1 = new Tuit();
        tuit1.setId(1L);
        Tuit tuit2 = new Tuit();
        tuit2.setId(tuit1.getId());
        assertThat(tuit1).isEqualTo(tuit2);
        tuit2.setId(2L);
        assertThat(tuit1).isNotEqualTo(tuit2);
        tuit1.setId(null);
        assertThat(tuit1).isNotEqualTo(tuit2);
    }
}
