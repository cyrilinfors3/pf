package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PfApp;

import com.mycompany.myapp.domain.Projectevolution;
import com.mycompany.myapp.domain.Project;
import com.mycompany.myapp.repository.ProjectevolutionRepository;
import com.mycompany.myapp.repository.search.ProjectevolutionSearchRepository;
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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProjectevolutionResource REST controller.
 *
 * @see ProjectevolutionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PfApp.class)
public class ProjectevolutionResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    @Autowired
    private ProjectevolutionRepository projectevolutionRepository;

    @Autowired
    private ProjectevolutionSearchRepository projectevolutionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProjectevolutionMockMvc;

    private Projectevolution projectevolution;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjectevolutionResource projectevolutionResource = new ProjectevolutionResource(projectevolutionRepository, projectevolutionSearchRepository);
        this.restProjectevolutionMockMvc = MockMvcBuilders.standaloneSetup(projectevolutionResource)
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
    public static Projectevolution createEntity(EntityManager em) {
        Projectevolution projectevolution = new Projectevolution()
            .title(DEFAULT_TITLE)
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE)
            .state(DEFAULT_STATE);
        // Add required entity
        Project project = ProjectResourceIntTest.createEntity(em);
        em.persist(project);
        em.flush();
        projectevolution.setProject(project);
        return projectevolution;
    }

    @Before
    public void initTest() {
        projectevolutionSearchRepository.deleteAll();
        projectevolution = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjectevolution() throws Exception {
        int databaseSizeBeforeCreate = projectevolutionRepository.findAll().size();

        // Create the Projectevolution
        restProjectevolutionMockMvc.perform(post("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectevolution)))
            .andExpect(status().isCreated());

        // Validate the Projectevolution in the database
        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeCreate + 1);
        Projectevolution testProjectevolution = projectevolutionList.get(projectevolutionList.size() - 1);
        assertThat(testProjectevolution.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProjectevolution.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testProjectevolution.getDocumentContentType()).isEqualTo(DEFAULT_DOCUMENT_CONTENT_TYPE);
        assertThat(testProjectevolution.getState()).isEqualTo(DEFAULT_STATE);

        // Validate the Projectevolution in Elasticsearch
        Projectevolution projectevolutionEs = projectevolutionSearchRepository.findOne(testProjectevolution.getId());
        assertThat(projectevolutionEs).isEqualToComparingFieldByField(testProjectevolution);
    }

    @Test
    @Transactional
    public void createProjectevolutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projectevolutionRepository.findAll().size();

        // Create the Projectevolution with an existing ID
        projectevolution.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectevolutionMockMvc.perform(post("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectevolution)))
            .andExpect(status().isBadRequest());

        // Validate the Projectevolution in the database
        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectevolutionRepository.findAll().size();
        // set the field null
        projectevolution.setTitle(null);

        // Create the Projectevolution, which fails.

        restProjectevolutionMockMvc.perform(post("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectevolution)))
            .andExpect(status().isBadRequest());

        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectevolutionRepository.findAll().size();
        // set the field null
        projectevolution.setDocument(null);

        // Create the Projectevolution, which fails.

        restProjectevolutionMockMvc.perform(post("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectevolution)))
            .andExpect(status().isBadRequest());

        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStateIsRequired() throws Exception {
        int databaseSizeBeforeTest = projectevolutionRepository.findAll().size();
        // set the field null
        projectevolution.setState(null);

        // Create the Projectevolution, which fails.

        restProjectevolutionMockMvc.perform(post("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectevolution)))
            .andExpect(status().isBadRequest());

        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjectevolutions() throws Exception {
        // Initialize the database
        projectevolutionRepository.saveAndFlush(projectevolution);

        // Get all the projectevolutionList
        restProjectevolutionMockMvc.perform(get("/api/projectevolutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectevolution.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void getProjectevolution() throws Exception {
        // Initialize the database
        projectevolutionRepository.saveAndFlush(projectevolution);

        // Get the projectevolution
        restProjectevolutionMockMvc.perform(get("/api/projectevolutions/{id}", projectevolution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projectevolution.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.documentContentType").value(DEFAULT_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.document").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT)))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProjectevolution() throws Exception {
        // Get the projectevolution
        restProjectevolutionMockMvc.perform(get("/api/projectevolutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjectevolution() throws Exception {
        // Initialize the database
        projectevolutionRepository.saveAndFlush(projectevolution);
        projectevolutionSearchRepository.save(projectevolution);
        int databaseSizeBeforeUpdate = projectevolutionRepository.findAll().size();

        // Update the projectevolution
        Projectevolution updatedProjectevolution = projectevolutionRepository.findOne(projectevolution.getId());
        // Disconnect from session so that the updates on updatedProjectevolution are not directly saved in db
        em.detach(updatedProjectevolution);
        updatedProjectevolution
            .title(UPDATED_TITLE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE)
            .state(UPDATED_STATE);

        restProjectevolutionMockMvc.perform(put("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjectevolution)))
            .andExpect(status().isOk());

        // Validate the Projectevolution in the database
        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeUpdate);
        Projectevolution testProjectevolution = projectevolutionList.get(projectevolutionList.size() - 1);
        assertThat(testProjectevolution.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProjectevolution.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testProjectevolution.getDocumentContentType()).isEqualTo(UPDATED_DOCUMENT_CONTENT_TYPE);
        assertThat(testProjectevolution.getState()).isEqualTo(UPDATED_STATE);

        // Validate the Projectevolution in Elasticsearch
        Projectevolution projectevolutionEs = projectevolutionSearchRepository.findOne(testProjectevolution.getId());
        assertThat(projectevolutionEs).isEqualToComparingFieldByField(testProjectevolution);
    }

    @Test
    @Transactional
    public void updateNonExistingProjectevolution() throws Exception {
        int databaseSizeBeforeUpdate = projectevolutionRepository.findAll().size();

        // Create the Projectevolution

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProjectevolutionMockMvc.perform(put("/api/projectevolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projectevolution)))
            .andExpect(status().isCreated());

        // Validate the Projectevolution in the database
        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProjectevolution() throws Exception {
        // Initialize the database
        projectevolutionRepository.saveAndFlush(projectevolution);
        projectevolutionSearchRepository.save(projectevolution);
        int databaseSizeBeforeDelete = projectevolutionRepository.findAll().size();

        // Get the projectevolution
        restProjectevolutionMockMvc.perform(delete("/api/projectevolutions/{id}", projectevolution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean projectevolutionExistsInEs = projectevolutionSearchRepository.exists(projectevolution.getId());
        assertThat(projectevolutionExistsInEs).isFalse();

        // Validate the database is empty
        List<Projectevolution> projectevolutionList = projectevolutionRepository.findAll();
        assertThat(projectevolutionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProjectevolution() throws Exception {
        // Initialize the database
        projectevolutionRepository.saveAndFlush(projectevolution);
        projectevolutionSearchRepository.save(projectevolution);

        // Search the projectevolution
        restProjectevolutionMockMvc.perform(get("/api/_search/projectevolutions?query=id:" + projectevolution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectevolution.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectevolution.class);
        Projectevolution projectevolution1 = new Projectevolution();
        projectevolution1.setId(1L);
        Projectevolution projectevolution2 = new Projectevolution();
        projectevolution2.setId(projectevolution1.getId());
        assertThat(projectevolution1).isEqualTo(projectevolution2);
        projectevolution2.setId(2L);
        assertThat(projectevolution1).isNotEqualTo(projectevolution2);
        projectevolution1.setId(null);
        assertThat(projectevolution1).isNotEqualTo(projectevolution2);
    }
}
