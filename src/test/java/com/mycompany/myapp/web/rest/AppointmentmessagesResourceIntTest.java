package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PfApp;

import com.mycompany.myapp.domain.Appointmentmessages;
import com.mycompany.myapp.repository.AppointmentmessagesRepository;
import com.mycompany.myapp.repository.search.AppointmentmessagesSearchRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.sameInstant;
import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AppointmentmessagesResource REST controller.
 *
 * @see AppointmentmessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfApp.class)
public class AppointmentmessagesResourceIntTest {

    private static final Long DEFAULT_PROJECT = 1L;
    private static final Long UPDATED_PROJECT = 2L;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_STATE = 1;
    private static final Integer UPDATED_STATE = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REPLY = "AAAAAAAAAA";
    private static final String UPDATED_REPLY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATEDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATEDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COACH = "AAAAAAAAAA";
    private static final String UPDATED_COACH = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    @Autowired
    private AppointmentmessagesRepository appointmentmessagesRepository;

    @Autowired
    private AppointmentmessagesSearchRepository appointmentmessagesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppointmentmessagesMockMvc;

    private Appointmentmessages appointmentmessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentmessagesResource appointmentmessagesResource = new AppointmentmessagesResource(appointmentmessagesRepository, appointmentmessagesSearchRepository);
        this.restAppointmentmessagesMockMvc = MockMvcBuilders.standaloneSetup(appointmentmessagesResource)
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
    public static Appointmentmessages createEntity(EntityManager em) {
        Appointmentmessages appointmentmessages = new Appointmentmessages()
            .project(DEFAULT_PROJECT)
            .date(DEFAULT_DATE)
            .state(DEFAULT_STATE)
            .message(DEFAULT_MESSAGE)
            .reply(DEFAULT_REPLY)
            .createdate(DEFAULT_CREATEDATE)
            .coach(DEFAULT_COACH)
            .owner(DEFAULT_OWNER);
        return appointmentmessages;
    }

    @Before
    public void initTest() {
        appointmentmessagesSearchRepository.deleteAll();
        appointmentmessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointmentmessages() throws Exception {
        int databaseSizeBeforeCreate = appointmentmessagesRepository.findAll().size();

        // Create the Appointmentmessages
        restAppointmentmessagesMockMvc.perform(post("/api/appointmentmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentmessages)))
            .andExpect(status().isCreated());

        // Validate the Appointmentmessages in the database
        List<Appointmentmessages> appointmentmessagesList = appointmentmessagesRepository.findAll();
        assertThat(appointmentmessagesList).hasSize(databaseSizeBeforeCreate + 1);
        Appointmentmessages testAppointmentmessages = appointmentmessagesList.get(appointmentmessagesList.size() - 1);
        assertThat(testAppointmentmessages.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testAppointmentmessages.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAppointmentmessages.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testAppointmentmessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testAppointmentmessages.getReply()).isEqualTo(DEFAULT_REPLY);
        assertThat(testAppointmentmessages.getCreatedate()).isEqualTo(DEFAULT_CREATEDATE);
        assertThat(testAppointmentmessages.getCoach()).isEqualTo(DEFAULT_COACH);
        assertThat(testAppointmentmessages.getOwner()).isEqualTo(DEFAULT_OWNER);

        // Validate the Appointmentmessages in Elasticsearch
        Appointmentmessages appointmentmessagesEs = appointmentmessagesSearchRepository.findOne(testAppointmentmessages.getId());
        assertThat(appointmentmessagesEs).isEqualToComparingFieldByField(testAppointmentmessages);
    }

    @Test
    @Transactional
    public void createAppointmentmessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentmessagesRepository.findAll().size();

        // Create the Appointmentmessages with an existing ID
        appointmentmessages.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentmessagesMockMvc.perform(post("/api/appointmentmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentmessages)))
            .andExpect(status().isBadRequest());

        // Validate the Appointmentmessages in the database
        List<Appointmentmessages> appointmentmessagesList = appointmentmessagesRepository.findAll();
        assertThat(appointmentmessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppointmentmessages() throws Exception {
        // Initialize the database
        appointmentmessagesRepository.saveAndFlush(appointmentmessages);

        // Get all the appointmentmessagesList
        restAppointmentmessagesMockMvc.perform(get("/api/appointmentmessages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointmentmessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].reply").value(hasItem(DEFAULT_REPLY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(sameInstant(DEFAULT_CREATEDATE))))
            .andExpect(jsonPath("$.[*].coach").value(hasItem(DEFAULT_COACH.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())));
    }

    @Test
    @Transactional
    public void getAppointmentmessages() throws Exception {
        // Initialize the database
        appointmentmessagesRepository.saveAndFlush(appointmentmessages);

        // Get the appointmentmessages
        restAppointmentmessagesMockMvc.perform(get("/api/appointmentmessages/{id}", appointmentmessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appointmentmessages.getId().intValue()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.reply").value(DEFAULT_REPLY.toString()))
            .andExpect(jsonPath("$.createdate").value(sameInstant(DEFAULT_CREATEDATE)))
            .andExpect(jsonPath("$.coach").value(DEFAULT_COACH.toString()))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppointmentmessages() throws Exception {
        // Get the appointmentmessages
        restAppointmentmessagesMockMvc.perform(get("/api/appointmentmessages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointmentmessages() throws Exception {
        // Initialize the database
        appointmentmessagesRepository.saveAndFlush(appointmentmessages);
        appointmentmessagesSearchRepository.save(appointmentmessages);
        int databaseSizeBeforeUpdate = appointmentmessagesRepository.findAll().size();

        // Update the appointmentmessages
        Appointmentmessages updatedAppointmentmessages = appointmentmessagesRepository.findOne(appointmentmessages.getId());
        // Disconnect from session so that the updates on updatedAppointmentmessages are not directly saved in db
        em.detach(updatedAppointmentmessages);
        updatedAppointmentmessages
            .project(UPDATED_PROJECT)
            .date(UPDATED_DATE)
            .state(UPDATED_STATE)
            .message(UPDATED_MESSAGE)
            .reply(UPDATED_REPLY)
            .createdate(UPDATED_CREATEDATE)
            .coach(UPDATED_COACH)
            .owner(UPDATED_OWNER);

        restAppointmentmessagesMockMvc.perform(put("/api/appointmentmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppointmentmessages)))
            .andExpect(status().isOk());

        // Validate the Appointmentmessages in the database
        List<Appointmentmessages> appointmentmessagesList = appointmentmessagesRepository.findAll();
        assertThat(appointmentmessagesList).hasSize(databaseSizeBeforeUpdate);
        Appointmentmessages testAppointmentmessages = appointmentmessagesList.get(appointmentmessagesList.size() - 1);
        assertThat(testAppointmentmessages.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testAppointmentmessages.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAppointmentmessages.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testAppointmentmessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAppointmentmessages.getReply()).isEqualTo(UPDATED_REPLY);
        assertThat(testAppointmentmessages.getCreatedate()).isEqualTo(UPDATED_CREATEDATE);
        assertThat(testAppointmentmessages.getCoach()).isEqualTo(UPDATED_COACH);
        assertThat(testAppointmentmessages.getOwner()).isEqualTo(UPDATED_OWNER);

        // Validate the Appointmentmessages in Elasticsearch
        Appointmentmessages appointmentmessagesEs = appointmentmessagesSearchRepository.findOne(testAppointmentmessages.getId());
        assertThat(appointmentmessagesEs).isEqualToComparingFieldByField(testAppointmentmessages);
    }

    @Test
    @Transactional
    public void updateNonExistingAppointmentmessages() throws Exception {
        int databaseSizeBeforeUpdate = appointmentmessagesRepository.findAll().size();

        // Create the Appointmentmessages

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppointmentmessagesMockMvc.perform(put("/api/appointmentmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointmentmessages)))
            .andExpect(status().isCreated());

        // Validate the Appointmentmessages in the database
        List<Appointmentmessages> appointmentmessagesList = appointmentmessagesRepository.findAll();
        assertThat(appointmentmessagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppointmentmessages() throws Exception {
        // Initialize the database
        appointmentmessagesRepository.saveAndFlush(appointmentmessages);
        appointmentmessagesSearchRepository.save(appointmentmessages);
        int databaseSizeBeforeDelete = appointmentmessagesRepository.findAll().size();

        // Get the appointmentmessages
        restAppointmentmessagesMockMvc.perform(delete("/api/appointmentmessages/{id}", appointmentmessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean appointmentmessagesExistsInEs = appointmentmessagesSearchRepository.exists(appointmentmessages.getId());
        assertThat(appointmentmessagesExistsInEs).isFalse();

        // Validate the database is empty
        List<Appointmentmessages> appointmentmessagesList = appointmentmessagesRepository.findAll();
        assertThat(appointmentmessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAppointmentmessages() throws Exception {
        // Initialize the database
        appointmentmessagesRepository.saveAndFlush(appointmentmessages);
        appointmentmessagesSearchRepository.save(appointmentmessages);

        // Search the appointmentmessages
        restAppointmentmessagesMockMvc.perform(get("/api/_search/appointmentmessages?query=id:" + appointmentmessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointmentmessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].reply").value(hasItem(DEFAULT_REPLY.toString())))
            .andExpect(jsonPath("$.[*].createdate").value(hasItem(sameInstant(DEFAULT_CREATEDATE))))
            .andExpect(jsonPath("$.[*].coach").value(hasItem(DEFAULT_COACH.toString())))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appointmentmessages.class);
        Appointmentmessages appointmentmessages1 = new Appointmentmessages();
        appointmentmessages1.setId(1L);
        Appointmentmessages appointmentmessages2 = new Appointmentmessages();
        appointmentmessages2.setId(appointmentmessages1.getId());
        assertThat(appointmentmessages1).isEqualTo(appointmentmessages2);
        appointmentmessages2.setId(2L);
        assertThat(appointmentmessages1).isNotEqualTo(appointmentmessages2);
        appointmentmessages1.setId(null);
        assertThat(appointmentmessages1).isNotEqualTo(appointmentmessages2);
    }
}
