package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PfApp;

import com.mycompany.myapp.domain.Projectmessages;
import com.mycompany.myapp.repository.ProjectmessagesRepository;
import com.mycompany.myapp.repository.search.ProjectmessagesSearchRepository;
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
import org.springframework.util.Base64Utils;

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
 * Test class for the ProjectmessagesResource REST controller.
 *
 * @see ProjectmessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfApp.class)
public class ProjectmessagesResourceIntTest {

    private static final Long DEFAULT_PROJECT = 1L;
    private static final Long UPDATED_PROJECT = 2L;

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SENDER = "AAAAAAAAAA";
    private static final String UPDATED_SENDER = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER = "BBBBBBBBBB";

    @Autowired
    private ProjectmessagesRepository projectmessagesRepository;

    @Autowired
    private ProjectmessagesSearchRepository projectmessagesSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectmessagesMockMvc;

    private Projectmessages projectmessages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectmessagesResource projectmessagesResource = new ProjectmessagesResource(projectmessagesRepository, projectmessagesSearchRepository);
        this.restProjectmessagesMockMvc = MockMvcBuilders.standaloneSetup(projectmessagesResource)
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
    public static Projectmessages createEntity(EntityManager em) {
        Projectmessages projectmessages = new Projectmessages()
            .project(DEFAULT_PROJECT)
            .date(DEFAULT_DATE)
            .message(DEFAULT_MESSAGE)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .sender(DEFAULT_SENDER)
            .receiver(DEFAULT_RECEIVER);
        return projectmessages;
    }

    @Before
    public void initTest() {
        projectmessagesSearchRepository.deleteAll();
        projectmessages = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectmessages() throws Exception {
        int databaseSizeBeforeCreate = projectmessagesRepository.findAll().size();

        // Create the Projectmessages
        restProjectmessagesMockMvc.perform(post("/api/projectmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectmessages)))
            .andExpect(status().isCreated());

        // Validate the Projectmessages in the database
        List<Projectmessages> projectmessagesList = projectmessagesRepository.findAll();
        assertThat(projectmessagesList).hasSize(databaseSizeBeforeCreate + 1);
        Projectmessages testProjectmessages = projectmessagesList.get(projectmessagesList.size() - 1);
        assertThat(testProjectmessages.getProject()).isEqualTo(DEFAULT_PROJECT);
        assertThat(testProjectmessages.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testProjectmessages.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testProjectmessages.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testProjectmessages.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testProjectmessages.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testProjectmessages.getReceiver()).isEqualTo(DEFAULT_RECEIVER);

        // Validate the Projectmessages in Elasticsearch
        Projectmessages projectmessagesEs = projectmessagesSearchRepository.findOne(testProjectmessages.getId());
        assertThat(projectmessagesEs).isEqualToComparingFieldByField(testProjectmessages);
    }

    @Test
    @Transactional
    public void createProjectmessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectmessagesRepository.findAll().size();

        // Create the Projectmessages with an existing ID
        projectmessages.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectmessagesMockMvc.perform(post("/api/projectmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectmessages)))
            .andExpect(status().isBadRequest());

        // Validate the Projectmessages in the database
        List<Projectmessages> projectmessagesList = projectmessagesRepository.findAll();
        assertThat(projectmessagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProjectmessages() throws Exception {
        // Initialize the database
        projectmessagesRepository.saveAndFlush(projectmessages);

        // Get all the projectmessagesList
        restProjectmessagesMockMvc.perform(get("/api/projectmessages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectmessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())));
    }

    @Test
    @Transactional
    public void getProjectmessages() throws Exception {
        // Initialize the database
        projectmessagesRepository.saveAndFlush(projectmessages);

        // Get the projectmessages
        restProjectmessagesMockMvc.perform(get("/api/projectmessages/{id}", projectmessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectmessages.getId().intValue()))
            .andExpect(jsonPath("$.project").value(DEFAULT_PROJECT.intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER.toString()))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectmessages() throws Exception {
        // Get the projectmessages
        restProjectmessagesMockMvc.perform(get("/api/projectmessages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectmessages() throws Exception {
        // Initialize the database
        projectmessagesRepository.saveAndFlush(projectmessages);
        projectmessagesSearchRepository.save(projectmessages);
        int databaseSizeBeforeUpdate = projectmessagesRepository.findAll().size();

        // Update the projectmessages
        Projectmessages updatedProjectmessages = projectmessagesRepository.findOne(projectmessages.getId());
        // Disconnect from session so that the updates on updatedProjectmessages are not directly saved in db
        em.detach(updatedProjectmessages);
        updatedProjectmessages
            .project(UPDATED_PROJECT)
            .date(UPDATED_DATE)
            .message(UPDATED_MESSAGE)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .sender(UPDATED_SENDER)
            .receiver(UPDATED_RECEIVER);

        restProjectmessagesMockMvc.perform(put("/api/projectmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectmessages)))
            .andExpect(status().isOk());

        // Validate the Projectmessages in the database
        List<Projectmessages> projectmessagesList = projectmessagesRepository.findAll();
        assertThat(projectmessagesList).hasSize(databaseSizeBeforeUpdate);
        Projectmessages testProjectmessages = projectmessagesList.get(projectmessagesList.size() - 1);
        assertThat(testProjectmessages.getProject()).isEqualTo(UPDATED_PROJECT);
        assertThat(testProjectmessages.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testProjectmessages.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testProjectmessages.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testProjectmessages.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testProjectmessages.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testProjectmessages.getReceiver()).isEqualTo(UPDATED_RECEIVER);

        // Validate the Projectmessages in Elasticsearch
        Projectmessages projectmessagesEs = projectmessagesSearchRepository.findOne(testProjectmessages.getId());
        assertThat(projectmessagesEs).isEqualToComparingFieldByField(testProjectmessages);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectmessages() throws Exception {
        int databaseSizeBeforeUpdate = projectmessagesRepository.findAll().size();

        // Create the Projectmessages

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectmessagesMockMvc.perform(put("/api/projectmessages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectmessages)))
            .andExpect(status().isCreated());

        // Validate the Projectmessages in the database
        List<Projectmessages> projectmessagesList = projectmessagesRepository.findAll();
        assertThat(projectmessagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectmessages() throws Exception {
        // Initialize the database
        projectmessagesRepository.saveAndFlush(projectmessages);
        projectmessagesSearchRepository.save(projectmessages);
        int databaseSizeBeforeDelete = projectmessagesRepository.findAll().size();

        // Get the projectmessages
        restProjectmessagesMockMvc.perform(delete("/api/projectmessages/{id}", projectmessages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean projectmessagesExistsInEs = projectmessagesSearchRepository.exists(projectmessages.getId());
        assertThat(projectmessagesExistsInEs).isFalse();

        // Validate the database is empty
        List<Projectmessages> projectmessagesList = projectmessagesRepository.findAll();
        assertThat(projectmessagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProjectmessages() throws Exception {
        // Initialize the database
        projectmessagesRepository.saveAndFlush(projectmessages);
        projectmessagesSearchRepository.save(projectmessages);

        // Search the projectmessages
        restProjectmessagesMockMvc.perform(get("/api/_search/projectmessages?query=id:" + projectmessages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectmessages.getId().intValue())))
            .andExpect(jsonPath("$.[*].project").value(hasItem(DEFAULT_PROJECT.intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER.toString())))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectmessages.class);
        Projectmessages projectmessages1 = new Projectmessages();
        projectmessages1.setId(1L);
        Projectmessages projectmessages2 = new Projectmessages();
        projectmessages2.setId(projectmessages1.getId());
        assertThat(projectmessages1).isEqualTo(projectmessages2);
        projectmessages2.setId(2L);
        assertThat(projectmessages1).isNotEqualTo(projectmessages2);
        projectmessages1.setId(null);
        assertThat(projectmessages1).isNotEqualTo(projectmessages2);
    }
}
